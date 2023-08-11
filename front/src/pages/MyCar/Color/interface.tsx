import { exteriorColorType } from '@contexts/MyCarContext';

export interface colorResponseInterface {
  carOptionId: number;
  colorUrl: string;
  price: number;
  tagResponses: {
    tagContent: string;
  }[];
}
export interface exteriorColorResponseInterface extends colorResponseInterface {
  optionName: exteriorColorType;
}

export interface interiorColorResponseInterface extends colorResponseInterface {
  optionName: string;
  photoUrl: string;
}

export type colorKey = 'exteriorColorResponses' | 'interiorColorResponses';

export interface colorInfoInterface {
  exteriorColorResponses: exteriorColorResponseInterface[];
  interiorColorResponses: interiorColorResponseInterface[];
}
