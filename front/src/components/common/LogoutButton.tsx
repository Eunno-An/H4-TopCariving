import { useNavigate } from 'react-router-dom';
import { Button } from '.';
import { useAlert } from '@contexts/AlertContext';
import { LoginUrl, apiInstance } from '@utils/api';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';

export const LogoutButton = () => {
  const { openAlert, closeAlert } = useAlert();
  const navigate = useNavigate();
  const confirmLogout = async () => {
    await apiInstance({
      url: `${LoginUrl.LOGOUT}`,
      method: 'GET',
    });
    closeAlert();
    sessionStorage.clear();
    localStorage.clear();
    navigate('/');
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
