import { Category, OptionWrapper } from '@components/archive/main';
import { ArchiveCard } from '@components/archive/main/ArchiveCard';
import { Flex } from '@components/common';
import styled from '@emotion/styled';
import { useState } from 'react';
import { useLoaderData, useNavigate } from 'react-router-dom';

export const ArchiveMain = () => {
  const [selectedMenu, setSelectedMenu] = useState(1);
  const [selectedOption, setSelectedOption] = useState<
    { carOptionId: number; optionName: string }[]
  >([]);
  const { archiveSearchResponses, options } =
    useLoaderData() as archiveMainInterface;

  const navigate = useNavigate();

  const onMoveDetail = (archivindId: number) => {
    navigate(`/archive/detail?id=${archivindId}`);
  };
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

export const oneContent = {
  archivingId: 1,
  model: {
    carOptionId: 1,
    optionName: 'Le Blanc',
    price: 41980000,
  },
  engine: {
    carOptionId: 5,
    optionName: '디젤 2.2',
    price: 0,
  },
  bodyType: {
    carOptionId: 7,
    optionName: '7인승',
    price: 0,
  },
  drivingMethod: {
    carOptionId: 9,
    optionName: '2WD',
    price: 0,
  },
  exteriorColor: {
    carOptionId: 11,
    optionName: '어비스블랙펄',
    price: 0,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/external_color/black.png',
  },
  interiorColor: {
    carOptionId: 17,
    optionName: '퀄팅천연(블랙)',
    price: 0,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/internal_color/black.png',
  },
  selectOptions: [
    {
      carOptionId: 103,
      optionName: '컴포트 II',
      price: 1090000,
      optionDetail: [
        {
          carOptionId: 1,
          optionName: '듀얼 와이드 선루프',
          price: 0,
        },
        {
          carOptionId: 2,
          optionName: '2열 통풍시트',
          price: 0,
        },
        {
          carOptionId: 3,
          optionName: '현대스마트센스1',
          price: 0,
        },
        {
          carOptionId: 4,
          optionName: '듀얼 머플러 패키지',
          price: 0,
        },
        {
          carOptionId: 5,
          optionName: '사이드스텝',
          price: 0,
        },
      ],
    },
    {
      carOptionId: 110,
      optionName: '주차보조 시스템 II',
      price: 690000,
      optionDetail: [
        {
          carOptionId: 111,
          optionName: '후방 주차 충돌방지 보조',
          price: 0,
        },
        {
          carOptionId: 112,
          optionName: '원격 스마트 주차 보조',
          price: 0,
        },
      ],
    },
  ],
};
export const archiveContent = [
  oneContent,
  oneContent,
  oneContent,
  oneContent,
  oneContent,
  oneContent,
];
