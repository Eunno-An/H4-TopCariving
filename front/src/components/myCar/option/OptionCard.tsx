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
  dimData,
  changeUserOptionList,
}: optionCardInterface) => {
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
            {dimData.split('\n').map((it, idx) => (
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
        padding="0 9px"
        gap={5}
        height={selectedMenu === cateName.select ? 104 : 80}
      >
        <div
          css={css`
            width: 125px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          `}
        >
          <OptionTitleName>{optionItem.optionName}</OptionTitleName>
        </div>
        {/* 가격 & 추가하기 & 추가완료 버튼은 '선택항목'일때만 보여진다 */}
        {selectedMenu === cateName.select && (
          <>
            {/* 가격 */}
            <Text typo="Body3_Medium">
              + {optionItem.price.toLocaleString()} 원
            </Text>
            {/* 버튼 */}
            <div
              onClick={() => {
                changeUserOptionList(idx);
              }}
            >
              <Button
                backgroundColor="White"
                heightType="small"
                width={142}
                border={4}
                css={isSelected ? selected : notSelected}
              >
                {isSelected ? (
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

const OptionTitleName = styled.span`
  ${theme.typo.Body3_Medium}
`;

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
  background-color: ${theme.palette.OptionBlue};

  position: relative;
`;

const notSelected = css`
  color: ${theme.palette.OptionBlue};
  border: 2px solid ${theme.palette.OptionBlue};

  position: relative;
`;

const Card = styled(Flex)<{ isSelected: boolean; selectedMenu: string }>`
  position: relative;
  width: 160px;
  height: ${({ selectedMenu }) =>
    selectedMenu === '선택항목' ? '197px' : '162px'};

  border-radius: 8px;

  background-color: ${({ isSelected, selectedMenu }) =>
    isSelected && selectedMenu === '선택항목'
      ? 'rgba(0, 44, 95, 0.1)'
      : theme.palette.LightSand};

  border: ${({ isSelected, selectedMenu }) =>
    isSelected && selectedMenu === '선택항목'
      ? `2px solid ${theme.palette.Primary}`
      : `2px solid ${theme.palette.LightSand}`};

  box-sizing: border-box;

  cursor: pointer;
`;

const ImgContainer = styled.img<{ selectedMenu: string }>`
  width: 156px;
  height: ${({ selectedMenu }) =>
    selectedMenu === '선택항목' ? '90px' : '79px'};

  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
`;

interface optionCardInterface {
  idx: number;
  isSelected: boolean;
  optionItem: selectOptionInterface | optionItemInterface;
  selectedMenu: string;
  dimData: string;
  changeUserOptionList: (optionIdx: number) => void;
}
