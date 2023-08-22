import { Button, Flex, Text } from '@components/common';
import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import hyundai from '@assets/images/hyundaiLogo.png';
import { initMyCarInfo, useMyCar } from '@contexts/MyCarContext';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { LoginUrl, apiInstance, token } from '@utils/api';
import { HyundaiOauthUrl } from '@assets/constant';
import { useToast } from '@contexts/ToastContext';

const Login = () => {
  const {
    register,
    handleSubmit,
    getValues,
    formState: { errors },
  } = useForm({ defaultValues: { email: '', password: '' } });

  const { setMyCarInfo } = useMyCar();
  useEffect(() => {
    if (
      localStorage.getItem('refreshToken') &&
      localStorage.getItem('refreshToken') !== String(undefined)
    ) {
      navigate('/my-car/trim');
    }
    localStorage.setItem('myCarInfo', JSON.stringify(initMyCarInfo));
    localStorage.setItem('archivingId', JSON.stringify(null));
    setMyCarInfo(initMyCarInfo);
  }, []);

  const { openToast } = useToast();
  const navigate = useNavigate();
  const onSubmit = async () => {
    const res = await apiInstance({
      url: LoginUrl.LOGIN,
      method: 'POST',
      bodyData: JSON.stringify({
        email: getValues().email,
        password: getValues().password,
      }),
    });

    if (res.accessToken) {
      token.accessToken = res.accessToken;
      localStorage.setItem('refreshToken', res.refreshToken);
      navigate('/my-car/trim');
    } else {
      openToast({
        newContent: '유저 정보가 존재하지 않아요',
      });
    }
  };

  const onMoveHyundai = () => {
    window.location.href = HyundaiOauthUrl;
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
        <form onSubmit={handleSubmit(onSubmit)}>
          <Flex direction="column" width={600} height="auto" gap={70}>
            <Flex direction="column" width={600} gap={12}>
              <Flex direction="column" gap={8} align="flex-start">
                <Text typo="Heading4_Medium">EMAIL</Text>
                <TextInput
                  placeholder="이메일을 입력해주세요"
                  {...register('email', {
                    required: '이메일을 입력해주세요',
                    pattern: {
                      value: /^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
                      message: '이메일 형식을 확인해주세요',
                    },
                  })}
                />
                <Flex height={15} width="auto">
                  <Text typo="Body3_Regular" palette="Primary">
                    {errors?.email?.message?.toString()}
                  </Text>
                </Flex>
              </Flex>
              <Flex direction="column" width={600} gap={8} align="flex-start">
                <Text typo="Heading4_Medium">PW</Text>
                <TextInput
                  placeholder="비밀번호를 입력해주세요"
                  type="password"
                  {...register('password', {
                    required: '비밀번호를 입력해주세요',
                  })}
                />
                <Flex height={20} width="auto">
                  <Text typo="Body3_Regular" palette="Primary">
                    {errors?.password?.message?.toString()}
                  </Text>
                </Flex>
                <Flex justify="flex-end">
                  <Text
                    onClick={onMoveHyundai}
                    css={css`
                      cursor: pointer;
                    `}
                  >
                    현대닷컴으로 로그인하기
                  </Text>
                </Flex>
              </Flex>
            </Flex>
            <Button
              width={600}
              heightType="medium"
              backgroundColor="Primary"
              typo="Heading4_Bold"
              type="submit"
            >
              로그인
            </Button>
          </Flex>
        </form>
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
