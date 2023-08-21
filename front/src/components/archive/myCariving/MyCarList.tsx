import { Button, Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { MyCarCard } from './MyCarCard';
import { FeedSaveCard } from '.';
import revertIcon from '@assets/images/revertIcon.svg';
import { css } from '@emotion/react';
import { useEffect, useState } from 'react';
import { MyArchiveUrl, apiInstance } from '@utils/api';

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

export const MyCarList = () => {
  const [isRemoved, setIsRemoved] = useState(false);
  const [removedId, setRemovedId] = useState<number>(-1);
  const [createCar, setCreateCar] = useState<createdMyCarInterface[]>([]);

  const deletedCar = (archivingId: number) => {
    setIsRemoved(true);
    setRemovedId(archivingId);
  };

  const revertDataFromServer = async () => {
    if (removedId !== -1) {
      await apiInstance({
        url: `${MyArchiveUrl.REVERT}/${removedId}`,
        method: 'POST',
      });
    }

    setIsRemoved(false);
    setRemovedId(-1);
  };

  useEffect(() => {
    const newCreatedCar = async () => {
      return await apiInstance({
        url: MyArchiveUrl.CREATED_CARS,
        method: 'GET',
      });
    };
    newCreatedCar().then((data) => setCreateCar(data));
    setIsRemoved(false);
  }, [isRemoved, removedId]);

  return (
    <Flex direction="column" justify="flex-start" gap={30}>
      <Flex
        width={1024}
        height={620}
        direction="column"
        justify="flex-start"
        gap={30}
      >
        <TitleContainer height={38} gap={30}>
          <Text typo="Heading3_Medium">내가 만든 차량 목록</Text>
        </TitleContainer>
        <CarCardContainer>
          {createCar.map((it) => (
            <MyCarCard
              key={`mycarcard_${it.archivingId}`}
              info={it}
              deletedCarId={(archivingId) => deletedCar(archivingId)}
            />
          ))}
        </CarCardContainer>
      </Flex>

      <RevertContainer>
        <Flex height={18} backgroundColor="LightSand"></Flex>
        {removedId !== -1 && (
          <RevertBox>
            <div onClick={revertDataFromServer}>
              <Button
                width={121}
                backgroundColor="Primary"
                heightType="medium"
                typo="Heading4_Bold"
              >
                <img
                  src={revertIcon}
                  alt=""
                  css={css`
                    padding-right: 5px;
                  `}
                />
                되돌리기
              </Button>
            </div>
          </RevertBox>
        )}
      </RevertContainer>

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

const RevertBox = styled(Flex)`
  position: absolute;
`;

const RevertContainer = styled(Flex)`
  position: relative;
`;

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
