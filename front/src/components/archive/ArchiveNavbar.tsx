import { Flex, Text } from '@components/common';
import navbarLeft from '@assets/images/navbarLeft.svg';
import cargoBlack from '@assets/images/cargoBlack.svg';
import car from '@assets/images/car.svg';
import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { useAlert } from '@contexts/AlertContext';
import { useNavigate } from 'react-router-dom';

export const ArchiveNavbar = ({
  pageTitle = '아카이빙',
}: {
  pageTitle?: string;
}) => {
  const onClickback = () => {
    window.history.back();
  };

  const { openAlert, closeAlert } = useAlert();

  const navigate = useNavigate();
  const onMoveMyCar = () => {
    navigate('/my-car/trim');
    closeAlert();
  };

  const onClickMoveMyCar = () => {
    openAlert({
      newContent: [
        '계속해서 내 차 만들기를 하시겠어요?',
        '내 차 만들기 화면으로 이동해요.',
      ],
      newButtonInfo: [
        { text: '취소', color: 'LightGray', onClick: closeAlert },
        { text: '확인', color: 'Primary', onClick: onMoveMyCar },
      ],
    });
  };

  return (
    <Flex height={91} backgroundColor="White">
      <Flex
        padding="23px 0 23px 0 "
        width={1040}
        css={css`
          position: relative;
        `}
      >
        <img
          src={navbarLeft}
          alt=""
          css={css`
            position: absolute;
            left: 0;
            cursor: pointer;
          `}
          onClick={onClickback}
        />
        <Flex
          backgroundColor="LightSand"
          borderRadius="18px"
          width="auto"
          height={40}
          gap={6}
          padding="4px 16px"
        >
          <img src={cargoBlack} color="red" alt="아카이빙 카고" />
          <Text palette="Black" typo="Heading3_Medium">
            {pageTitle}
          </Text>
        </Flex>
        <MyCarDirect
          gap={8}
          width="auto"
          height={35}
          onClick={onClickMoveMyCar}
          css={css`
            position: absolute;
            right: 0;
            cursor: pointer;
          `}
        >
          <img src={car} alt="" />
          <Text typo="Heading4_Medium">내 차 만들기 바로가기</Text>
        </MyCarDirect>
      </Flex>
    </Flex>
  );
};

const MyCarDirect = styled(Flex)`
  border-bottom: 2px solid black;
`;
