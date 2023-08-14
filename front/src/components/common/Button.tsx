import styled from '@emotion/styled';
import { KeyOfPalette, KeyOfTypo, theme } from '@styles/theme';

export const Button = styled.button<{
  width?: number | 'auto' | string;
  heightType?: 'large' | 'medium' | 'small';
  backgroundColor: KeyOfPalette;
  padding?: string;
  border?: number;
  typo?: KeyOfTypo;
}>`
  display: flex;
  justify-content: center;
  align-items: center;
  width: ${({ width }) => (typeof width === 'number' ? `${width}px` : width)};
  height: ${({ heightType }) =>
    heightType === 'large'
      ? '56px'
      : heightType === 'medium'
      ? '50px'
      : '30px'};

  background-color: ${({ backgroundColor }) => theme.palette[backgroundColor]};
  color: ${({ backgroundColor }) =>
    backgroundColor === 'Primary'
      ? theme.palette.White
      : backgroundColor === 'LightGray'
      ? theme.palette.DarkGray
      : backgroundColor === 'LightSand'
      ? theme.palette.Black
      : theme.palette.Primary};

  padding: ${({ padding, width }) =>
    padding ? padding : width === 'auto' ? '8px 24px' : '0'};

  border-radius: ${({ border }) => (border ? `${border}px` : '8px')};
  border: none;
  box-sizing: border-box;

  ${({ typo }) => (typo ? theme.typo[typo] : '')};
  cursor: pointer;
`;
