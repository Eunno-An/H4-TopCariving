import { Flex, Tag, Text } from '@components/common';
import styled from '@emotion/styled';
import { optionName } from '@pages/Archive/main';
import { theme } from '@styles/theme';
import { OptionChip } from '.';
import { Dispatch, SetStateAction } from 'react';
import { css } from '@emotion/react';

interface ArchiveCardProps {
  selectedOption: {
    id: number;
    name: string;
  }[];
  setSelectedOption: Dispatch<
    SetStateAction<
      {
        id: number;
        name: string;
      }[]
    >
  >;
}

export const ArchiveCard = ({
  selectedOption,
  setSelectedOption,
}: ArchiveCardProps) => {
  const setNewSelectedOption = (isSelected: boolean, idx: number) => {
    const newOptionList = isSelected
      ? selectedOption.filter((item) => item.id != optionName[idx].id)
      : [
          ...selectedOption,
          { id: optionName[idx].id, name: optionName[idx].name },
        ];
    setSelectedOption(newOptionList);
  };

  return (
    <Wrapper direction="column" justify="flex-start" align="flex-start">
      <Flex height="auto" justify="space-between" margin="0 0 5px 0">
        <Text typo="Heading3_Bold">펠리세이드 Le Blanc</Text>
        <BrownTag>23년 7월 19일에 시승했어요</BrownTag>
      </Flex>
      <Text typo="Body3_Regular">디젤 2.2 / 4WD / 7인승</Text>
      <Flex height="auto" gap={12} justify="flex-start" margin="12px 0 10px 0">
        <Text typo="Body3_Medium">외장</Text>
        <Text typo="Body3_Regular" palette="DarkGray">
          문라이트 블루펄
        </Text>
        <Text typo="Body3_Medium">내장</Text>
        <Text typo="Body3_Regular" palette="DarkGray">
          퀼팅 천연(블랙)
        </Text>
      </Flex>
      <Flex
        height="auto"
        gap={7}
        justify="flex-start"
        align="flex-start"
        margin="0 0 16px 0"
      >
        <Text typo="Body3_Medium">선택옵션</Text>
        <Flex
          height="auto"
          justify="flex-start"
          gap={8}
          css={css`
            flex-wrap: wrap;
          `}
        >
          {optionName.slice(0, 4).map((item, idx) => {
            const isSelected = selectedOption.some(
              (option) => option.id === item.id,
            );
            return (
              <OptionChip
                key={item.id}
                isSelected={isSelected}
                onClick={() => setNewSelectedOption(isSelected, idx)}
              >
                <Text typo="Body3_Regular">{item.name}</Text>
              </OptionChip>
            );
          })}
        </Flex>
      </Flex>
      <Review>
        승차감이 좋아요 차가 크고 운전하는 시야도 높아서 좋았어요 저는 13개월
        아들이 있는데 뒤에 차시트 달아도 널널할 것 같습니다. 다른 주차 관련
        옵션도 괜찮아요.
      </Review>
      <Flex
        height="auto"
        justify="flex-start"
        gap={8}
        css={css`
          flex-wrap: wrap;
        `}
      >
        {[
          '편리해요',
          '이것만 있으면 나도 주차고수🚘',
          '대형견도 문제 없어요🐶',
        ].map((desc) => (
          <Tag desc={desc} />
        ))}
      </Flex>
    </Wrapper>
  );
};

const Wrapper = styled(Flex)`
  width: 508px;
  border: 3px solid ${theme.palette.Sand};
  border-radius: 8px;
  padding: 30px 20px;
`;

const BrownTag = styled(Flex)`
  display: flex;
  width: auto;
  height: 24px;
  padding: 2px 12px;
  justify-content: center;
  align-items: center;
  gap: 10px;
  flex-shrink: 0;

  border-radius: 16px;
  background-color: ${theme.palette.LightSand};
  color: ${theme.palette.Gold};
`;

const Review = styled.div`
  display: inline-flex;
  width: 425px;
  white-space: normal;
  line-height: 22px;

  padding: 15px 21px;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin: 0 0 21px 0;

  border-radius: 8px;
  background: ${theme.palette.LightSand};
`;
