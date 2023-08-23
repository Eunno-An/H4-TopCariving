import { Button, Flex, masonryLayout } from '@components/common';
import styled from '@emotion/styled';
import { MyCarCard } from './MyCarCard';
import revertIcon from '@assets/images/revertIcon.svg';
import { css } from '@emotion/react';
import { useEffect, useRef, useState } from 'react';
import { MyArchiveUrl, apiInstance } from '@utils/api';
import { MenuName, OptionMenu } from '@pages/MyCar/Option';
import { archiveSearchResponsesInterface } from '@pages/Archive/main';
import { ArchiveCard } from '../main';
import { useNavigate } from 'react-router-dom';
import { useToast } from '@contexts/ToastContext';

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
  const [pageNum, setPageNum] = useState(0);
  const [removedId, setRemovedId] = useState<number>(-1);
  const [removedCar, setRemovedCar] = useState<createdMyCarInterface[]>([]);
  const [createCar, setCreateCar] = useState<createdMyCarInterface[]>([]);
  const [bookmarkCar, setBookmarkCar] = useState<
    archiveSearchResponsesInterface[]
  >([]);
  const [selectedMenu, setSelectedMenu] = useState('내가 만든 차량 목록');

  const getCreatedCar = async () => {
    const data = await apiInstance({
      url: `${MyArchiveUrl.CREATED_CARS}?pageNumber=${pageNum + 1}&pageSize=8`,
      method: 'GET',
    });
    setCreateCar([...data]);
  };

  const getBookMarkCar = async () => {
    const data = (await apiInstance({
      url: `${MyArchiveUrl.FEED}?pageNumber=${pageNum + 1}&pageSize=8`,
      method: 'GET',
    })) as archiveSearchResponsesInterface[];
    setBookmarkCar(data);
  };

  const { openToast } = useToast();

  const deletedCar = (archivingId: number) => {
    openToast({
      newContent: '삭제되었어요. 되돌리기를 누르면 취소할 수 있어요.',
    });
    setRemovedCar([...createCar]);
    setCreateCar((it) => it.filter((item) => item.archivingId !== archivingId));
    setRemovedId(archivingId);
  };

  const revertDeletedCar = async () => {
    if (removedId !== -1) {
      await apiInstance({
        url: `${MyArchiveUrl.CREATED_CARS}/${removedId}`,
        method: 'POST',
      });
      openToast({ newContent: '삭제가 취소되었어요.' });
      setCreateCar([...removedCar]);
      setRemovedCar([]);
      setRemovedId(-1);
    }
  };

  const navigate = useNavigate();
  const onMoveDetail = (archivindId: number) => {
    navigate(`/archive/detail?id=${archivindId}`);
  };

  const masonryRef = useRef<HTMLDivElement>(null);
  useEffect(() => {
    masonryLayout({ element: masonryRef });
  }, [selectedMenu, createCar, bookmarkCar]);

  useEffect(() => {
    getBookMarkCar();
  }, [removedId, pageNum]);

  useEffect(() => {
    if (createCar.length <= 4) {
      getCreatedCar();
    }
  }, [createCar]);

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
                setPageNum(0);
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
                setPageNum(0);
              }
            }}
          >
            {cateName.savedCar}
          </MyCarivingMenuName>
        </OptionMenu>
        <CardContainer ref={masonryRef}>
          {selectedMenu === cateName.myCar
            ? createCar.map((it) => (
                <div
                  key={`mycarcard_${it.archivingId}`}
                  onClick={() => onMoveDetail(it.archivingId)}
                >
                  <MyCarCard
                    info={it}
                    deletedCarId={(archivingId) => deletedCar(archivingId)}
                  />
                </div>
              ))
            : bookmarkCar.map((bookmarkInfo, idx) => (
                <div
                  key={`mychivingCard_${idx}`}
                  onClick={() => onMoveDetail(bookmarkInfo.archivingId)}
                >
                  <ArchiveCard
                    key={`mychivingCard_${idx}`}
                    archiveInfo={bookmarkInfo}
                  />
                </div>
              ))}
        </CardContainer>
      </Flex>
      <RevertBox isShow={removedId !== -1}>
        <div onClick={revertDeletedCar}>
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
                z-index: 2;
              `}
            />
            되돌리기
          </Button>
        </div>
      </RevertBox>
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
  grid-auto-rows: 10px;

  width: 1048px;
  gap: 25px;
  padding: 0 0 60px 0;
`;

const MyCarivingMenuName = styled(MenuName)`
  &::after {
    height: 4px;
  }
`;
