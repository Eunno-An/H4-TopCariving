import { Category, OptionWrapper } from '@components/archive/main';
import { ArchiveCard } from '@components/archive/main/ArchiveCard';
import { Flex, Text, masonryLayout } from '@components/common';
import styled from '@emotion/styled';
import { ArchiveUrl, apiInstance } from '@utils/api';
import { useEffect, useRef, useState } from 'react';
import { useLoaderData, useNavigate } from 'react-router-dom';

export const ArchiveMain = () => {
  const [selectedMenu, setSelectedMenu] = useState(1);
  const [selectedOption, setSelectedOption] = useState<
    { carOptionId: number; optionName: string }[]
  >([]);
  const [pageNum, setPageNum] = useState(0);
  const { initArchiveSearchResponses, initOptions } =
    useLoaderData() as initArchiveMainInterface;

  const [archiveSearchResponses, setArchiveSearchResponses] = useState<
    archiveSearchResponsesInterface[]
  >(initArchiveSearchResponses);

  const [options, setOptions] =
    useState<{ carOptionId: number; optionName: string }[]>(initOptions);

  const navigate = useNavigate();

  const onMoveDetail = (archivindId: number) => {
    navigate(`/archive/detail?id=${archivindId}`);
  };

  useEffect(() => {
    let param = '';
    selectedOption.forEach((item, idx) => {
      param += `optionIds=${item.carOptionId}`;
      // 마지막 항목이 아닐 때만 '&'를 추가
      if (idx < selectedOption.length - 1) {
        param += '&';
      }
    });

    const getData = async () => {
      const { archiveSearchResponses, options } = (await apiInstance({
        url: `${ArchiveUrl.MAIN_RESULT}?pageNumber=${
          pageNum + 1
        }&pageSize=8&${param}`,
        method: 'GET',
      })) as archiveMainInterface;
      setArchiveSearchResponses(archiveSearchResponses);
      setOptions(options);
      setPageNum(pageNum + 1);
    };

    getData();
  }, [selectedOption]);

  const masonryRef = useRef<HTMLDivElement>(null);
  useEffect(() => {
    masonryLayout({ element: masonryRef });
  }, [archiveSearchResponses, selectedMenu]);

  return (
    <Flex direction="column" justify="flex-start">
      <Category selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
      <OptionWrapper
        options={options}
        selectedOption={selectedOption}
        setSelectedOption={setSelectedOption}
      />
      <>
        {selectedMenu === 1 && archiveSearchResponses.length !== 0 ? (
          <Container ref={masonryRef}>
            {archiveSearchResponses.map((archiveInfo, idx) => (
              <div
                key={`archiveCard_${idx}`}
                onClick={() => onMoveDetail(archiveInfo.archivingId)}
              >
                <ArchiveCard
                  archiveInfo={archiveInfo}
                  selectedOption={selectedOption}
                />
              </div>
            ))}
          </Container>
        ) : (
          <Flex width={1048}>
            <Text typo="Body1_Regular">검색 결과가 존재하지 않아요.</Text>
          </Flex>
        )}
      </>
    </Flex>
  );
};

export const Container = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-auto-rows: 10px;

  width: 1048px;
  gap: 25px;
  padding: 30px 0 60px 0;
`;

export type archiveType =
  | '내장색상'
  | '바디타입'
  | '모델'
  | '엔진'
  | '외장색상'
  | '상세 품목'
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
  '펠리세이드',
  '베뉴',
  '코나',
  '싼타페',
  '그랜저',
  '아반떼',
  '아이오닉',
];
