import styled from '@emotion/styled';
import { KeyOfPalette, theme } from '@styles/theme';

export const Button = styled.button<{
  width?: number | 'auto';
  heightType?: 'large' | 'medium' | 'small';
  backgroundColor: KeyOfPalette;
  padding?: string;
  border?: number;
}>`
  display: flex;
  justify-content: center;
  align-items: center;
  width: ${({ width }) => (width ? `${width}px` : 'auto')};
  height: ${({ heightType }) =>
    heightType === 'large'
      ? '56px'
      : heightType === 'medium'
      ? '50px'
      : '30px'};

  background-color: ${({ backgroundColor }) => theme.palette[backgroundColor]};
  color: ${({ backgroundColor }) =>
    backgroundColor === 'Primary' ? theme.palette.White : theme.palette.White}

  padding: ${({ padding }) => (padding ? padding : '0')};

  border-radius: ${({ border }) => (border ? `${border}px` : '8px')};
  border: none;
  box-sizing: border-box;

  cursor: pointer;
`;
