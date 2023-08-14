import { Flex, Text } from '@components/common';
import vector484 from '@assets/images/Vector 484.svg';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
export const ArchiveReview = () => {
  return (
    <Flex direction="column" height={334} backgroundColor="LightSand">
      <CarInfo
        direction="column"
        backgroundColor="LightSand"
        width={1040}
        height={150}
        gap={16}
      >
        <Flex direction="column" align="flex-start">
          <Flex justify="flex-start" gap={25}>
            <Text typo="Heading1_Bold">펠리세이드 Le Blanc</Text>
            <InfoTag>
              <Text typo="Body4_Medium" palette="Gold">
                23년 7월 19일에 시승했어요
              </Text>
            </InfoTag>
          </Flex>
          <Text typo="Body1_Regular">디젤 2.2 / 4WD / 7인승</Text>
        </Flex>

        <Flex justify="flex-start" gap={16} height={22}>
          <Flex gap={12} width="auto">
            <Text typo="Body3_Medium">외장</Text>
            <Text typo="Body3_Regular" palette="DarkGray">
              문라이트 블루펄
            </Text>
          </Flex>
          <img src={vector484} alt="" />
          <Flex gap={12} width="auto">
            <Text typo="Body3_Medium">내장</Text>
            <Text typo="Body3_Regular" palette="DarkGray">
              퀼팅 천연(블랙)
            </Text>
          </Flex>
        </Flex>
        <CarContinaer src={`/image/exterior/black/image_001.png`} />
      </CarInfo>

      <Flex
        justify="flex-start"
        margin="16px 0 16px 0"
        width={1040}
        height={134}
      >
        <ReviewContainer
          width={347}
          borderRadius="8px"
          backgroundColor="White"
          padding="12px 17px"
        >
          <Text typo="Body3_Regular">
            승차감이 좋아요 차가 크고 운전하는 시야도 높아서 좋았어요 저는
            13개월 아들이 있는데 뒤에 차시트 달아도 널널할 것 같습니다. 다른
            주차 관련 옵션도 괜찮아요.승차감이 좋아요 차가 크고 운전하는 시야도
            높아서 좋았어요.운전하는 시야도 높아서 좋았어요
          </Text>
        </ReviewContainer>
      </Flex>
    </Flex>
  );
};

const ReviewContainer = styled(Flex)`
  border: 1px solid ${theme.palette.LightGray};
`;

const CarInfo = styled(Flex)`
  position: relative;
  padding-bottom: 22px;
  border-bottom: 1px solid ${theme.palette.MediumGray};
`;

const InfoTag = styled(Flex)`
  width: auto;
  height: 22px;
  padding: 12px;

  border-radius: 8px;
  background: ${theme.palette.Sand};

  white-space: nowrap;
`;

const CarContinaer = styled.img`
  position: absolute;
  right: -200px;
  top: -80px;

  @keyframes movingCar {
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
  }
`;
