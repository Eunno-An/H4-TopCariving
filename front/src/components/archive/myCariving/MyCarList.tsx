import { Button, Flex, masonryLayout } from '@components/common';
import styled from '@emotion/styled';
import { MyCarCard } from './MyCarCard';
import { FeedSaveCard } from '.';
import revertIcon from '@assets/images/revertIcon.svg';
import { css } from '@emotion/react';
import { useEffect, useRef, useState } from 'react';
import { MyArchiveUrl, apiInstance } from '@utils/api';
import { MenuName, OptionMenu } from '@pages/MyCar/Option';

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

const cateName = {
  myCar: '내가 만든 차량 목록',
  savedCar: '피드에서 저장한 차량 목록',
};

export const MyCarList = () => {
  const [isRemoved, setIsRemoved] = useState(false);
  const [removedId, setRemovedId] = useState<number>(-1);
  const [createCar, setCreateCar] = useState<createdMyCarInterface[]>([]);
  const [selectedMenu, setSelectedMenu] = useState('내가 만든 차량 목록');

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

  const masonryRef = useRef<HTMLDivElement>(null);
  useEffect(() => {
    masonryLayout({ element: masonryRef });
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
        <OptionMenu justify="flex-start" height={40} gap={23}>
          <MyCarivingMenuName
            isSelected={selectedMenu === cateName.myCar}
            onClick={() => {
              if (selectedMenu !== cateName.myCar) {
                setSelectedMenu(cateName.myCar);
              }
            }}
          >
            {cateName.myCar}
          </MyCarivingMenuName>
          <MyCarivingMenuName
            isSelected={selectedMenu === cateName.savedCar}
            onClick={() => {
              if (selectedMenu !== cateName.savedCar) {
                setSelectedMenu(cateName.savedCar);
              }
            }}
          >
            {cateName.savedCar}
          </MyCarivingMenuName>
        </OptionMenu>
        <CardContainer ref={masonryRef}>
          {selectedMenu === cateName.myCar
            ? createCar.map((it) => (
                <MyCarCard
                  key={`mycarcard_${it.archivingId}`}
                  info={it}
                  deletedCarId={(archivingId) => deletedCar(archivingId)}
                />
              ))
            : Array(8)
                .fill(0)
                .map((_, idx) => <FeedSaveCard key={`feedCard_${idx}`} />)}
        </CardContainer>
      </Flex>
      <Flex
        css={css`
          position: relative;
        `}
      >
        {/* {removedId !== -1 && ( */}
        <RevertBox isShow={removedId !== -1}>
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
        {/* )} */}
      </Flex>
    </Flex>
  );
};

const RevertBox = styled.div<{ isShow: boolean }>`
  position: fixed;
  bottom: 30px;
  height: auto;
  width: auto;
  z-index: 2;

  opacity: ${({ isShow }) => (isShow ? 1 : 0)};
  transition: opacity 0.3s;
`;

const CardContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  // grid-auto-rows: 10px;

  width: 1048px;
  gap: 25px;
  padding: 0 0 60px 0;
`;

const MyCarivingMenuName = styled(MenuName)`
  &::after {
    height: 4px;
  }
`;
