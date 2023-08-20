import { Flex, Text } from '@components/common';
import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';

export interface TrimCardInterface {
  carOptionId: number;
  optionName: string;
  photos: { content: string; photoPngUrl: string; photoSvgUrl: string }[];
  price: number;
}

export const TrimCard = ({
  trim,
  isSelected,
}: {
  trim: TrimCardInterface;
  isSelected: boolean;
}) => {
  const { optionName, photos, price } = trim;

  return (
    <CustomFlex
      direction="column"
      width={242}
      height={251}
      borderRadius="8px"
      padding="11px"
      gap={16}
      backgroundColor="LightSand"
      isSelected={isSelected === true}
    >
      <Text typo="Heading3_Bold" palette={isSelected ? 'Primary' : 'Black'}>
        {optionName}
      </Text>
      <img src="/image/page/myCar/rowLine.svg" />
      <Flex justify="space-between">
        {photos.map((item, key) => (
          <Flex
            direction="column"
            justify="center"
            align="center"
            key={`trimCard_${key}`}
            gap={4}
          >
            <img
              src={item.photoSvgUrl}
              style={{
                filter: isSelected
                  ? 'invert(12%) sepia(40%) saturate(4856%) hue-rotate(196deg) brightness(50%) contrast(106%)'
                  : '',
              }}
            />
            <Flex direction="column">
              {item.content.split('\\n').map((name: string, key: number) => (
                <Text
                  typo="Caption_Medium"
                  palette={isSelected ? 'Primary' : 'DarkGray'}
                  key={`imgOption_${key}`}
                  css={css`
                    white-space: nowrap;
                  `}
                >
                  {name}
                </Text>
              ))}
            </Flex>
          </Flex>
        ))}
      </Flex>
      <img src="/image/page/myCar/rowLine.svg" />
      <Text typo="Heading3_Bold" palette={isSelected ? 'Primary' : 'Black'}>
        {`${price.toLocaleString()}원`}
      </Text>
    </CustomFlex>
  );
};

const CustomFlex = styled(Flex)<{ isSelected: boolean }>`
  background-color: ${({ isSelected }) =>
    isSelected ? 'rgba(0, 44, 95, 0.10)' : theme.palette.LightSand};
  border: ${({ isSelected }) =>
    isSelected
      ? `2px solid ${theme.palette.Primary}`
      : `2px solid ${theme.palette.White}`};
  box-sizing: border-box;
  cursor: pointer;

  &:hover {
    border: 2px solid ${theme.palette.Primary};
  }
  transition: ease 0.3s;
`;
