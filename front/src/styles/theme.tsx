import { palette } from './palette';
import { typo } from './typo';

export interface TypeofTheme {
  palette: TypeOfPalette;
  typo: TypeOfTypo;
}

export const theme: TypeofTheme = {
  palette,
  typo,
};

export type TypeOfPalette = typeof palette;
export type KeyOfPalette = keyof typeof palette;

export type TypeOfTypo = typeof typo;
export type KeyOfTypo = keyof typeof typo;

export type KeyOfAlertPalette = keyof typeof palette.Alert;
