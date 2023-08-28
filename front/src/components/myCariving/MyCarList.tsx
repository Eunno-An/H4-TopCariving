import { Button, Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { MyCarCard } from './MyCarCard';
import revertIcon from '@assets/images/revertIcon.svg';
import { css } from '@emotion/react';
import { useEffect, useRef, useState } from 'react';
import { MyArchiveUrl, apiInstance } from '@utils/api';
import { MenuName, OptionMenu } from '@pages/MyCar/Option';
import { archiveSearchResponsesInterface } from '@pages/Archive/main';
import { ArchiveCard } from '../archive/main';
import { useNavigate } from 'react-router-dom';
import { useToast } from '@contexts/ToastContext';
import { pageSize } from '@assets/constant';
import { useInfiniteScroll } from '@hooks/useInfiniteScroll';
import { masonryLayout } from '@utils/masonryLayout';

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
  made: '내가 만든 차량 목록',
  bookmarked: '피드에서 저장한 차량 목록',
};

export const MyCarList = () => {
  const [myCarPageNum, setMyCarPageNum] = useState(0);
  const [bookMarkPageNum, setBookMarkPageNum] = useState(0);

  const [removedId, setRemovedId] = useState<number>(-1);
  const [removedCar, setRemovedCar] = useState<createdMyCarInterface[]>([]);
  const [createCar, setCreateCar] = useState<createdMyCarInterface[]>([]);
  const [bookmarkCar, setBookmarkCar] = useState<
    archiveSearchResponsesInterface[]
  >([]);
  const [selectedMenu, setSelectedMenu] = useState('내가 만든 차량 목록');

  const { openToast } = useToast();
  const masonryRef = useRef<HTMLDivElement>(null);
  const madeCardRef = useRef<HTMLDivElement>(null);
  const BookMarkCardRef = useRef<HTMLDivElement>(null);

  const navigate = useNavigate();
  const onMoveDetail = (archivindId: number) => {
    navigate(`/archive/detail?id=${archivindId}`);
  };

  useEffect(() => {
    masonryLayout({ element: masonryRef });
  }, [selectedMenu, createCar, bookmarkCar]);

  useEffect(() => {
    if (selectedMenu === cateName.made) getCreatedCar();
  }, [selectedMenu, myCarPageNum]);

  useEffect(() => {
    if (selectedMenu === cateName.bookmarked) getBookMarkCar();
  }, [selectedMenu, bookMarkPageNum]);

  useEffect(() => {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    useInfiniteScroll({
      element: madeCardRef,
      pageNum: myCarPageNum,
      setPageNum: setMyCarPageNum,
    });
  }, [createCar, madeCardRef]);

  useEffect(() => {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    useInfiniteScroll({
      element: BookMarkCardRef,
      pageNum: bookMarkPageNum,
      setPageNum: setBookMarkPageNum,
    });
  }, [bookmarkCar, BookMarkCardRef]);

  const getCreatedCar = async () => {
    const data = await apiInstance({
      url: `${MyArchiveUrl.CREATED_CARS}?pageNumber=${
        myCarPageNum + 1
      }&pageSize=${pageSize}`,
      method: 'GET',
    });
    setCreateCar([...createCar, ...data]);
  };

  const getBookMarkCar = async () => {
    const data = (await apiInstance({
      url: `${MyArchiveUrl.FEED}?pageNumber=${
        bookMarkPageNum + 1
      }&pageSize=${pageSize}`,
      method: 'GET',
    })) as archiveSearchResponsesInterface[];

    setBookmarkCar([...bookmarkCar, ...data]);
  };

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

  return (
    <Flex direction="column" justify="flex-start" gap={30}>
      <Flex height={620} direction="column" justify="flex-start" gap={30}>
        <OptionMenu
          justify="flex-start"
          width={1048}
          height={40}
          gap={23}
          backgroundColor="White"
          css={css`
            position: fixed;
            z-index: 6;
            top: 151px;
          `}
        >
          <MyCarivingMenuName
            isSelected={selectedMenu === cateName.made}
            onClick={() => {
              if (selectedMenu !== cateName.made) {
                setSelectedMenu(cateName.made);
              }
            }}
          >
            {cateName.made}
          </MyCarivingMenuName>
          <MyCarivingMenuName
            isSelected={selectedMenu === cateName.bookmarked}
            onClick={() => {
              if (selectedMenu !== cateName.bookmarked) {
                setSelectedMenu(cateName.bookmarked);
              }
            }}
          >
            {cateName.bookmarked}
          </MyCarivingMenuName>
        </OptionMenu>
        <TopMargin />
        <CardContainer ref={masonryRef}>
          {selectedMenu === cateName.made ? (
            createCar.length !== 0 ? (
              createCar.map((it, idx) => (
                <div
                  key={`mycarcard_${it.archivingId}`}
                  onClick={() => onMoveDetail(it.archivingId)}
                  ref={idx === createCar.length - 1 ? madeCardRef : null}
                >
                  <MyCarCard
                    info={it}
                    deletedCarId={(archivingId) => deletedCar(archivingId)}
                  />
                </div>
              ))
            ) : (
              <Flex width={1048}>
                <Text typo="Body1_Regular">
                  내가 만든 차량이 존재하지 않아요.
                </Text>
              </Flex>
            )
          ) : bookmarkCar.length !== 0 ? (
            bookmarkCar.map((bookmarkInfo, idx) => (
              <div
                key={`mychivingCard_${idx}`}
                onClick={() => onMoveDetail(bookmarkInfo.archivingId)}
                ref={idx === bookmarkCar.length - 1 ? BookMarkCardRef : null}
              >
                <ArchiveCard
                  key={`mychivingCard_${idx}`}
                  archiveInfo={bookmarkInfo}
                />
              </div>
            ))
          ) : (
            <Flex width={1048}>
              <Text typo="Body1_Regular">
                피드에 저장한 차량이 존재하지 않아요.
              </Text>
            </Flex>
          )}
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

const TopMargin = styled.div`
  height: 40px;
  flex-shrink: 0;
`;

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
  padding: 0 0 160px 0;
`;

const MyCarivingMenuName = styled(MenuName)`
  &::after {
    height: 4px;
  }
`;
