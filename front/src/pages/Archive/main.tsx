import { Category, OptionWrapper } from '@components/archive/main';
import { ArchiveCard } from '@components/archive/main/ArchiveCard';
import { Flex } from '@components/common';
import styled from '@emotion/styled';
import { ArchiveUrl, apiInstance } from '@utils/api';
import { useEffect, useState } from 'react';
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

  return (
    <Flex direction="column" justify="flex-start">
      <Category selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
      <OptionWrapper
        options={options}
        selectedOption={selectedOption}
        setSelectedOption={setSelectedOption}
      />
      <Grid>
        {selectedMenu === 1 ? (
          archiveSearchResponses.length !== 0 ? (
            archiveSearchResponses.map((archiveInfo, idx) => (
              <div
                key={`archiveCard_${idx}`}
                onClick={() => onMoveDetail(archiveInfo.archivingId)}
              >
                <ArchiveCard
                  options={options}
                  archiveInfo={archiveInfo}
                  selectedOption={selectedOption}
                />
              </div>
            ))
          ) : (
            <Flex>검색 결과가 없습니다.</Flex>
          )
        ) : (
          <Flex>검색 결과가 없습니다.</Flex>
        )}
      </Grid>
    </Flex>
  );
};

const Grid = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);

  overflow: auto;
  white-space: nowrap;

  padding: 24px 0;
  gap: 30px;

  ::-webkit-scrollbar {
    display: none;
  }
`;

export type archiveType =
  | '내장색상'
  | '바디타입'
  | '모델'
  | '엔진'
  | '외장색상'
  | '상세 품목'
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

export const optionName = [
  { carOptionId: 1, optionName: '듀얼 와이드 선루프' },
  { carOptionId: 2, optionName: '2열 통풍시트' },
  { carOptionId: 3, optionName: '현대스마트센스1' },
  { carOptionId: 4, optionName: '듀얼 머플러 패키지' },
  { carOptionId: 5, optionName: '사이드스텝' },
  { carOptionId: 6, optionName: '차량 보호 필름' },
  { carOptionId: 7, optionName: '듀얼 와이드 선루프' },
  { carOptionId: 8, optionName: '2열 통풍시트' },
  { carOptionId: 9, optionName: '현대스마트센스1' },
  { carOptionId: 10, optionName: '듀얼 머플러 패키지' },
  { carOptionId: 11, optionName: '사이드스텝' },
  { carOptionId: 12, optionName: '차량 보호 필름' },
  { carOptionId: 13, optionName: '듀얼 와이드 선루프' },
  { carOptionId: 14, optionName: '2열 통풍시트' },
  { carOptionId: 15, optionName: '현대스마트센스1' },
];
