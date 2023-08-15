import { Flex, Text } from '@components/common';
import navbarLeft from '@assets/images/navbarLeft.svg';
import cargoBlack from '@assets/images/cargoBlack.svg';
import car from '@assets/images/car.svg';
import styled from '@emotion/styled';

export const ArchiveNavbar = ({
  pageTitle = '아카이빙',
}: {
  pageTitle?: string;
}) => {
  return (
    <Flex height={91} backgroundColor="White">
      <Flex justify="space-between" padding="23px 0 23px 0 " width={1040}>
        <img src={navbarLeft} alt="" />
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
        <MyCarDirect gap={8} width="auto">
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
