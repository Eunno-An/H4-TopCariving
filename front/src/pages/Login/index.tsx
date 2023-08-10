import { Button, Flex, Text } from '@components/common';
import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { useNavigate } from 'react-router-dom';
import hyundai from '@assets/images/hyundaiLogo.png';

const Login = () => {
  const navigate = useNavigate();
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
            <Button width={600} heightType="medium" backgroundColor="Primary">
              <Text palette="White" typo="Heading4_Bold">
                로그인
              </Text>
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
  ${theme.typo.Body2_Medium}

  padding: 0 10px;
  box-sizing: border-box;

  border: 1px solid ${theme.palette.Primary};
  border-radius: 8px;
`;
