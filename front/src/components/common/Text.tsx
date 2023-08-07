import styled from '@emotion/styled';
import {
  KeyOfPalette,
  KeyOfAlertPalette,
  KeyOfTypo,
  theme,
} from '@styles/theme';

export const Text = styled.div<{
  typo?: KeyOfTypo;
  palette?: KeyOfPalette;
  alertPalette?: KeyOfAlertPalette;
  margin?: string;
}>`
  ${({ typo }) => (typo ? theme.typo[typo] : '')};
  color: ${({ palette, alertPalette }) =>
    palette
      ? theme.palette[palette]
      : alertPalette
      ? theme.palette.Alert[alertPalette]
      : ''};
  display: flex;
  align-items: center;
  margin: ${({ margin }) => (margin ? margin : '0')};
`;
