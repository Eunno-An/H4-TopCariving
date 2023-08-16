import { Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { MyCarCard } from './MyCarCard';
import { FeedSaveCard } from '.';

const createdCars = [
  {
    archivingId: 1,
    trims: {
      additionalProp1: '디젤2.2',
      additionalProp2: '4WD',
      additionalProp3: '7인승',
    },
    dayTime: '2023-08-15T06:28:10.376Z',
    selectedOptions: [
      {
        name: '후방 주차 충돌방지 보조',
        photoUrl:
          'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/pca.jpeg',
      },
      {
        name: '후방 주차 충돌방지 보조',
        photoUrl:
          'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/pca.jpeg',
      },
      {
        name: '후방 주차 충돌방지 보조',
        photoUrl:
          'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/pca.jpeg',
      },
    ],
    complete: false,
  },
  {
    archivingId: 2,
    trims: {
      additionalProp1: '디젤4.3',
      additionalProp2: '2WD',
      additionalProp3: '8인승',
    },
    dayTime: '2023-08-10T06:28:10.376Z',
    selectedOptions: [
      {
        name: '3열 열선시트',
        photoUrl:
          'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/3_heated.jpeg',
      },
      {
        name: '메탈 도어스커프',
        photoUrl:
          'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/metaldoorscuff.jpeg',
      },
    ],
    complete: false,
  },
  {
    archivingId: 3,
    trims: {
      additionalProp1: '디젤2.2',
      additionalProp2: '4WD',
      additionalProp3: '7인승',
    },
    dayTime: '2023-08-15T06:28:10.376Z',
    selectedOptions: [
      {
        name: '메탈 도어스커프',
        photoUrl:
          'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/metaldoorscuff.jpeg',
      },
      {
        name: '3열 열선시트',
        photoUrl:
          'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/3_heated.jpeg',
      },
      {
        name: '후석 승객 알림',
        photoUrl:
          'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/roa.jpeg',
      },
    ],
    complete: true,
  },
  {
    archivingId: 4,
    trims: {
      additionalProp1: '디젤2.2',
      additionalProp2: '4WD',
      additionalProp3: '7인승',
    },
    dayTime: '2023-08-15T06:28:10.376Z',
    selectedOptions: [
      {
        name: '후석 승객 알림',
        photoUrl:
          'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/roa.jpeg',
      },
    ],
    complete: false,
  },
];

export const MyCarList = () => {
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
          {createdCars.map((it) => (
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
