import { Flex, Text } from '@components/common';
import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { Dispatch, SetStateAction } from 'react';

// 4:14
interface OptionWrapperInterface {
  options: { carOptionId: number; optionName: string }[];
  selectedOption: {
    carOptionId: number;
    optionName: string;
  }[];
  setSelectedOption: Dispatch<
    SetStateAction<
      {
        carOptionId: number;
        optionName: string;
      }[]
    >
  >;
}

export const OptionWrapper = ({
  options,
  selectedOption,
  setSelectedOption,
}: OptionWrapperInterface) => {
  const setNewSelectedOption = (isSelected: boolean, idx: number) => {
    const newOptionList = isSelected
      ? selectedOption.filter(
          (item) => item.carOptionId != options[idx].carOptionId,
        )
      : [
          ...selectedOption,
          {
            carOptionId: options[idx].carOptionId,
            optionName: options[idx].optionName,
          },
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
        {options.map((item, idx) => {
          const isSelected = selectedOption.some(
            (option) => option.carOptionId === item.carOptionId,
          );
          return (
            <OptionChip
              key={item.carOptionId}
              isSelected={isSelected}
              onClick={() => setNewSelectedOption(isSelected, idx)}
            >
              <Text typo="Body3_Regular">{item.optionName}</Text>
            </OptionChip>
          );
        })}
      </Flex>
    </Flex>
  );
};

export const OptionChip = styled.div<{ isSelected: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;

  padding: 2px 8px;

  border-radius: 4px;
  border: 0.5px solid ${theme.palette.LightGray};
  background: ${({ isSelected }) =>
    isSelected ? theme.palette.OptionBlue : theme.palette.White};
  color: ${({ isSelected }) =>
    isSelected ? theme.palette.White : theme.palette.Black};

  cursor: pointer;
`;
