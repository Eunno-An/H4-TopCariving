import { Flex, Text } from '@components/common';
import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { optionName } from '@pages/Archive/main';
import { theme } from '@styles/theme';
import { Dispatch, SetStateAction } from 'react';

interface OptionWrapperInterface {
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

export const OptionWrapper = ({
  selectedOption,
  setSelectedOption,
}: OptionWrapperInterface) => {
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
    <Flex backgroundColor="LightSand" height="auto">
      <Flex
        height="auto"
        padding="25px 0"
        gap={14}
        justify="flex-start"
        width={1048}
        css={css`
          flex-wrap: wrap;
        `}
      >
        {optionName.map((item, idx) => {
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
  );
};

export const OptionChip = styled.div<{ isSelected: boolean }>`
  display: inline-flex;
  padding: 4px 12px;
  justify-content: center;
  align-items: center;
  gap: 10px;

  border-radius: 4px;
  border: 0.5px solid ${theme.palette.LightGray};
  background: ${({ isSelected }) =>
    isSelected ? theme.palette.OptionBlue : theme.palette.White};
  color: ${({ isSelected }) =>
    isSelected ? theme.palette.White : theme.palette.Black};

  cursor: pointer;
`;
