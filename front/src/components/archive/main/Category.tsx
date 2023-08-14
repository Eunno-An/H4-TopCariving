import { Flex, Text } from '@components/common';
import { css } from '@emotion/react';
import { cateName } from '@pages/Archive/main';
import { theme } from '@styles/theme';
import { Dispatch, SetStateAction } from 'react';

interface CagoryInterface {
  selectedMenu: number;
  setSelectedMenu: Dispatch<SetStateAction<number>>;
}

export const Category = ({
  selectedMenu,
  setSelectedMenu,
}: CagoryInterface) => {
  return (
    <Flex
      height={38}
      gap={90}
      css={css`
        border-bottom: 1px solid ${theme.palette.LightGray};
      `}
    >
      {cateName.map((cate, idx) => (
        <Text
          typo="Heading3_Bold"
          palette={selectedMenu === idx ? 'Black' : 'LightGray'}
          onClick={() => setSelectedMenu(idx)}
          css={textCss(selectedMenu === idx)}
        >
          {cate}
        </Text>
      ))}
    </Flex>
  );
};

const textCss = (isSelected: boolean) => css`
  cursor: pointer;
  position: relative; /* 부모 요소에 대해 상대 위치 설정 */
  white-space: nowrap;
  &::after {
    content: '';
    position: absolute;
    left: 0;
    bottom: -5px;
    width: 100%;
    height: 5px;
    background-color: ${theme.palette.Black};
    opacity: ${isSelected ? 1 : 0};
    transition: opacity 0.3s;
  }
`;
