import { Flex, Text, Button } from '@components/common';
import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { useState } from 'react';
import check from '@assets/images/check.svg';
import {
  optionItemInterface,
  selectOptionInterface,
} from '@pages/MyCar/Option';
import { DimInfoMsg } from '.';

const cateName = {
  select: '선택항목',
  default: '기본 포함 품목',
};

export const OptionCard = ({
  idx,
  isSelected,
  optionItem,
  selectedMenu,
  changeUserOptionList,
}: {
  idx: number;
  isSelected: boolean;
  optionItem: selectOptionInterface | optionItemInterface;
  selectedMenu: string;
  changeUserOptionList: (optionIdx: number) => void;
}) => {
  const [isAddBtnClicked, setIsBtnClicked] = useState(isSelected);
  const [hover, setHover] = useState(false);
  return (
    <Card
      direction="column"
      isSelected={isSelected}
      selectedMenu={selectedMenu}
      onMouseEnter={() => selectedMenu === cateName.select && setHover(true)}
      onMouseLeave={() => setHover(false)}
    >
      {selectedMenu === cateName.select && (
        <Dim isHover={hover}>
          <DimContent>
            {dummyInfoData[idx].map((it, idx) => (
              <DimInfoMsg key={`dimInfo_${idx}`} desc={it} />
            ))}
          </DimContent>
        </Dim>
      )}
      {/* 이미지 컨테이너 */}
      <Flex
        width={160}
        height={selectedMenu === cateName.select ? 93 : 82}
        borderRadius="8px"
      >
        <ImgContainer src={optionItem.photoUrl} selectedMenu={selectedMenu} />
      </Flex>
      {/* 옵션이름 */}
      <Flex
        direction="column"
        align="flex-start"
        padding="20px 9px"
        gap={8}
        height={selectedMenu === cateName.select ? 104 : 80}
      >
        <Text typo="Body3_Medium" style={{ whiteSpace: 'nowrap' }}>
          {optionItem.optionName}
        </Text>
        {/* 가격 & 추가하기 & 추가완료 버튼은 '선택항목'일때만 보여진다 */}
        {selectedMenu === cateName.select && (
          <>
            {/* 가격 */}
            <Text typo="Body3_Medium">
              + {optionItem.price.toLocaleString('ko-KR')} 원
            </Text>
            {/* 버튼 */}
            <div
              onClick={() => {
                changeUserOptionList(idx);
                setIsBtnClicked(!isAddBtnClicked);
              }}
            >
              <Button
                backgroundColor="White"
                heightType="small"
                width={142}
                border={4}
                css={isAddBtnClicked ? selected : notSelected}
              >
                {isAddBtnClicked ? (
                  <Flex gap={2}>
                    <Text typo="Body3_Medium">추가완료</Text>
                    <img src={check} alt="" />
                  </Flex>
                ) : (
                  <Text typo="Body3_Medium">추가하기</Text>
                )}
              </Button>
            </div>
          </>
        )}
        {/* 선택항목일때 기본포함을 표시 */}
        {selectedMenu !== cateName.select && (
          <Text typo="Body3_Medium" palette="MediumGray">
            기본포함
          </Text>
        )}
      </Flex>
    </Card>
  );
};

const DimContent = styled(Flex)`
  height: 140px;
  flex-direction: column;
  justify-content: flex-start;

  overflow: scroll;

  ::-webkit-scrollbar {
    display: none;
  }
`;

const Dim = styled(Flex)<{ isHover: boolean }>`
  display: ${({ isHover }) => (isHover ? 'block' : 'none')};
  position: absolute;

  flex-direction: column;
  padding: 10px;
  justify-content: flex-start;
  align-items: flex-start;

  width: 160px;
  height: 197px;

  border-radius: 8px;

  background-color: ${({ isHover }) =>
    isHover ? 'rgba(35, 35, 35, 0.75);' : 'none'};
`;

const selected = css`
  color: ${theme.palette.White};
  background-color: #385da2;

  position: relative;
`;

const notSelected = css`
  color: #385da2;
  border: 2px solid #385da2;

  position: relative;
`;

const Card = styled(Flex)<{ isSelected: boolean; selectedMenu: string }>`
  position: relative;
  width: 160px;
  height: ${({ selectedMenu }) =>
    selectedMenu === '선택항목' ? '197px' : '162px'};

  border-radius: 8px;

  background-color: ${({ isSelected }) =>
    isSelected ? 'rgba(0, 44, 95, 0.1)' : theme.palette.LightSand};

  border: ${({ isSelected }) =>
    isSelected
      ? `2px solid ${theme.palette.Primary}`
      : `2px solid ${theme.palette.LightSand}`};

  box-sizing: border-box;

  cursor: pointer;
`;

const ImgContainer = styled.img<{ selectedMenu: string }>`
  width: 157px;
  height: ${({ selectedMenu }) =>
    selectedMenu === '선택항목' ? '90px' : '79px'};

  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
`;

const dummyInfoData = [
  [
    '전방 충돌방지 보조(교차 차량/추월시 대향차/측방 접근차)',
    '내비게이션 기반 스마트 크루즈 컨트롤(진출입로)',
    '고속도로 주행 보조',
  ],
  ['전방 충돌방지 보조', '내비게이션 기반 스마트 크루즈'],
  ['내비게이션 기반 스마트 크루즈 컨트롤(진출입로)', '고속도로 주행 보조'],
  [
    '전방 충돌방지 보조(교차 차량/추월시 대향차/측방 접근차)',
    '내비게이션 기반 스마트 크루즈 컨트롤(진출입로)',
    '고속도로 주행 보조',
    '내비게이션 기반 스마트 크루즈 컨트롤(진출입로)',
    '내비게이션 기반 스마트 크루즈 컨트롤(진출입로)',
    '내비게이션 기반 스마트 크루즈 컨트롤(진출입로)',
    '내비게이션 기반 스마트 크루즈 컨트롤(진출입로)',
  ],
  [
    '전방 충돌방지 보조(교차 차량/추월시 대향차/측방 접근차)',
    '내비게이션 기반 스마트 크루즈 컨트롤(진출입로)',
    '고속도로 주행 보조',
    '전방 충돌방지 보조(교차 차량/추월시 대향차/측방 접근차)',
  ],
  ['전방 충돌방지 보조(교차 차량/추월시 대향차/측방 접근차)'],
];
