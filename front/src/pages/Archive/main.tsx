import { Category, OptionWrapper } from '@components/archive/main';
import { ArchiveCard } from '@components/archive/main/ArchiveCard';
import { Flex } from '@components/common';
import styled from '@emotion/styled';
import { useState } from 'react';

export const ArchiveMain = () => {
  const [selectedMenu, setSelectedMenu] = useState(1);
  const [selectedOption, setSelectedOption] = useState<
    { id: number; name: string }[]
  >([]);
  return (
    <Flex direction="column" justify="flex-start">
      <Category selectedMenu={selectedMenu} setSelectedMenu={setSelectedMenu} />
      <OptionWrapper
        selectedOption={selectedOption}
        setSelectedOption={setSelectedOption}
      />
      <Grid>
        {selectedMenu === 1 ? (
          archiveContent.map(() => (
            <ArchiveCard
              selectedOption={selectedOption}
              setSelectedOption={setSelectedOption}
            />
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
  { id: 1, name: '듀얼 와이드 선루프' },
  { id: 2, name: '2열 통풍시트' },
  { id: 3, name: '현대스마트센스1' },
  { id: 4, name: '듀얼 머플러 패키지' },
  { id: 5, name: '사이드스텝' },
  { id: 6, name: '차량 보호 필름' },
  { id: 7, name: '듀얼 와이드 선루프' },
  { id: 8, name: '2열 통풍시트' },
  { id: 9, name: '현대스마트센스1' },
  { id: 10, name: '듀얼 머플러 패키지' },
  { id: 11, name: '사이드스텝' },
  { id: 12, name: '차량 보호 필름' },
  { id: 13, name: '듀얼 와이드 선루프' },
  { id: 14, name: '2열 통풍시트' },
  { id: 15, name: '현대스마트센스1' },
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
