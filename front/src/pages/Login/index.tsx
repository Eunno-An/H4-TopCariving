import { Button, Flex, Text } from '@components/common';
import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { useNavigate } from 'react-router-dom';
import hyundai from '@assets/images/hyundaiLogo.png';
import { initMyCarInfo } from '@contexts/MyCarContext';
import { useEffect } from 'react';

const Login = () => {
  const navigate = useNavigate();

  useEffect(() => {
    localStorage.clear();
    localStorage.setItem('myCarInfo', JSON.stringify(initMyCarInfo));
    localStorage.setItem('archivingId', JSON.stringify(null));
  }, []);

  const onSubmit = () => {
    navigate('/my-car/trim');
  };

  return (
    <Flex direction="column" align="center" justify="center">
      <Flex direction="column" width="auto" height="auto">
        <img
          src={hyundai}
          css={css`
            width: 300px;
            margin-top: -100px;
          `}
        />
        <Flex direction="column" width={600} height="auto" gap={70}>
          <Flex direction="column" gap={12}>
            <Flex direction="column" gap={8} align="flex-start">
              <Text typo="Heading4_Medium">ID</Text>
              <TextInput placeholder="아이디를 입력해주세요" />
            </Flex>
            <Flex direction="column" gap={8} align="flex-start">
              <Text typo="Heading4_Medium">PW</Text>
              <TextInput placeholder="비밀번호를 입력해주세요" />
            </Flex>
          </Flex>

          <div onClick={onSubmit}>
            <Button
              width={600}
              heightType="medium"
              backgroundColor="Primary"
              typo="Heading4_Bold"
            >
              로그인
            </Button>
          </div>
        </Flex>
      </Flex>
    </Flex>
  );
};

export default Login;

const TextInput = styled.input`
  width: 100%;
  height: 50px;
  ${theme.typo.Body2_Regular}

  padding: 0 10px;
  box-sizing: border-box;

  border-radius: 8px;
  border: none;
  background: var(--1, #f4f6f9);
`;
