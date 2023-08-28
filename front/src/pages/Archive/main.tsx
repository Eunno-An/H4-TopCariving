import { pageSize } from '@assets/constant';
import { Category, OptionWrapper } from '@components/archive/main';
import { ArchiveCard } from '@components/archive/main/ArchiveCard';
import { Flex, Text } from '@components/common';
import { ArchiveUrl, apiInstance } from '@utils/api';

import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from '@emotion/styled';
import { useInfiniteScroll } from '@hooks/useInfiniteScroll';
import { masonryLayout } from '@utils/masonryLayout';
export const ArchiveMain = () => {
  const [archiveSearchResponses, setArchiveSearchResponses] = useState<
    archiveSearchResponsesInterface[]
  >([]);
  const [options, setOptions] = useState<
    { carOptionId: number; optionName: string }[]
  >([]);

  const [selectedMenu, setSelectedMenu] = useState(1);
  const [selectedOption, setSelectedOption] = useState<
    { carOptionId: number; optionName: string }[]
  >([]);
  const [pageNum, setPageNum] = useState(0);

  const archiveCardRef = useRef<HTMLDivElement>(null);
  const masonryRef = useRef<HTMLDivElement>(null);

  const navigate = useNavigate();
  const onMoveDetail = (archivindId: number) => {
    navigate(`/archive/detail?id=${archivindId}`);
  };

  const getData = async () => {
    let param = '';
    selectedOption.forEach((item, idx) => {
      param += `optionIds=${item.carOptionId}`;
      if (idx < selectedOption.length - 1) {
        param += '&';
      }
    });

    const { archiveSearchResponses, options } = (await apiInstance({
      url: `${ArchiveUrl.MAIN_RESULT}?pageNumber=${
        pageNum + 1
      }&pageSize=${pageSize}&${param}`,
      method: 'GET',
    })) as archiveMainInterface;

    return {
      newArchiveSearchResponses: archiveSearchResponses,
      newOptions: options,
    };
  };

  useEffect(() => {
    const setData = async () => {
      const { newArchiveSearchResponses, newOptions } = await getData();
      setArchiveSearchResponses(newArchiveSearchResponses);
      setOptions(newOptions);
    };
    setData();
  }, []);

  useEffect(() => {
    setPageNum(0);
    const setData = async () => {
      const { newArchiveSearchResponses } = await getData();
      setArchiveSearchResponses(newArchiveSearchResponses);
    };
    setData();
    window.scrollTo(0, 0);
  }, [selectedOption]);

  useEffect(() => {
    const setData = async () => {
      const { newArchiveSearchResponses } = await getData();
      setArchiveSearchResponses([
        ...archiveSearchResponses,
        ...newArchiveSearchResponses,
      ]);
    };
    setData();
  }, [pageNum]);

  useEffect(() => {
    masonryLayout({ element: masonryRef });
  }, [archiveSearchResponses, selectedMenu]);

  useEffect(() => {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    useInfiniteScroll({
      element: archiveCardRef,
      pageNum: pageNum,
      setPageNum: setPageNum,
    });
  }, [archiveSearchResponses, archiveCardRef, pageNum]);

  return (
    <Flex direction="column" justify="flex-start">
      <Category selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
      <OptionWrapper
        options={options}
        selectedOption={selectedOption}
        setSelectedOption={setSelectedOption}
      />
      <TopMargin />
      <>
        {selectedMenu === 1 && archiveSearchResponses.length !== 0 ? (
          <Container ref={masonryRef}>
            {archiveSearchResponses.map((archiveInfo, idx) => (
              <div
                key={`archiveCard_${idx}`}
                onClick={() => onMoveDetail(archiveInfo.archivingId)}
                ref={
                  idx === archiveSearchResponses.length - 1
                    ? archiveCardRef
                    : null
                }
              >
                <ArchiveCard
                  archiveInfo={archiveInfo}
                  selectedOption={selectedOption}
                />
              </div>
            ))}
          </Container>
        ) : (
          <Flex width={1048} height={300}>
            <Text typo="Heading3_Medium">검색 결과가 존재하지 않아요.</Text>
          </Flex>
        )}
      </>
    </Flex>
  );
};

const TopMargin = styled.div`
  height: 320px;
  flex-shrink: 0;
`;

export const Container = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-auto-rows: 10px;

  width: 1048px;
  gap: 25px;
  padding: 30px 0 160px 0;
`;

export type archiveType =
  | '내장색상'
  | '바디타입'
  | '모델'
  | '엔진'
  | '외장색상'
  | '상세 품목'
  | '선택품목'
  | 'H Genuine Accessories'
  | 'N performance'
  | '구동방식';

export interface archiveSearchResponsesInterface {
  archivingId: number;
  carArchiveResult: {
    [key in archiveType]: string[];
  };
  dayTime: string;
  carReview: string;
  tags: {
    tagContent: string;
  }[];
  type: string;
}

export interface archiveMainInterface {
  options: { carOptionId: number; optionName: string }[];
  archiveSearchResponses: archiveSearchResponsesInterface[];
}

export interface initArchiveMainInterface {
  initOptions: { carOptionId: number; optionName: string }[];
  initArchiveSearchResponses: archiveSearchResponsesInterface[];
}

export const cateName = [
  '전체',
  '팰리세이드',
  '베뉴',
  '코나',
  '싼타페',
  '그랜저',
  '아반떼',
  '아이오닉',
];
