import { useNavigate } from 'react-router-dom';
import { Button } from '.';
import { useAlert } from '@contexts/AlertContext';
import { LoginUrl, token } from '@utils/api';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';

const SERVER_URL = import.meta.env.VITE_SERVER_URL;

export const LogoutButton = () => {
  const { openAlert, closeAlert } = useAlert();
  const navigate = useNavigate();

  const confirmLogout = async () => {
    const data = await fetch(`${SERVER_URL}${LoginUrl.LOGOUT}`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${token.accessToken}`,
      },
      credentials: 'include',
    }).then((res) => {
      return res;
    });

    if (data) {
      localStorage.clear();
      closeAlert();
      navigate('/');
    }
  };

  const onClickLogout = () => {
    openAlert({
      newContent: ['로그아웃 하시겠어요?'],
      newButtonInfo: [
        { text: '취소', color: 'LightGray', onClick: closeAlert },
        { text: '확인', color: 'Primary', onClick: confirmLogout },
      ],
    });
  };

  return (
    <ButtonContainer backgroundColor="LightSand" onClick={onClickLogout}>
      로그아웃
    </ButtonContainer>
  );
};

const ButtonContainer = styled(Button)`
  width: 80px;
  height: 35px;
  ${theme.typo.Body2_Medium};
`;
