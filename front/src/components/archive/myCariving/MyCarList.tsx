import { Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { MyCarCard } from './MyCarCard';
import { FeedSaveCard } from '.';
import { useEffect } from 'react';

export interface createdMyCarInterface {
  archivingId: number;
  trims: {
    바디타입: string;
    모델: string;
    엔진: string;
    구동방식: string;
  };
  dayTime: Date;
  selectedOptions: {
    name: string;
    photoUrl: string;
  }[];
  complete: boolean;
}

export const MyCarList = ({
  createdMyCar,
}: {
  createdMyCar: createdMyCarInterface[];
}) => {
  useEffect(() => {
    createdMyCar.map((it) => console.log(it));
  }, []);
  return (
    <Flex direction="column" justify="flex-start" gap={30}>
      <Flex
        width={1024}
        height={620}
        direction="column"
        justify="flex-start"
        gap={30}
      >
        <TitleContainer height={38}>
          <Text typo="Heading3_Medium">내가 만든 차량 목록</Text>
        </TitleContainer>
        <CarCardContainer>
          {createdMyCar.map((it) => (
            <MyCarCard key={`mycarcard_${it.archivingId}`} info={it} />
          ))}
        </CarCardContainer>
      </Flex>

      <Flex>
        <Flex height={18} backgroundColor="LightSand"></Flex>
      </Flex>

      <Flex width={1024} direction="column" justify="flex-start" gap={30}>
        <TitleContainer height={38}>
          <Text typo="Heading3_Medium">피드에서 저장한 차량 목록</Text>
        </TitleContainer>
        <FeedContainer>
          <FeedSaveCard />
          <FeedSaveCard />
          <FeedSaveCard />
          <FeedSaveCard />
        </FeedContainer>
      </Flex>
    </Flex>
  );
};

const FeedContainer = styled(Flex)`
  flex-wrap: wrap;
  gap: 8px;

  justify-content: flex-start;
  align-items: flex-start;
`;

const CarCardContainer = styled(Flex)`
  flex-wrap: wrap;
  gap: 12px;

  justify-content: flex-start;
  align-items: flex-start;
`;

const TitleContainer = styled(Flex)`
  justify-content: flex-start;
  border-bottom: 4px solid ${theme.palette.Sand};
`;
