export interface myCarFooterInterface {
  name: string[];
  color: { exteriorColorResponses: string; interiorColorResponses: string };
  option: string[];
  price: number;
}

export interface myCarOptionInterface {
  carOptionId: number;
  optionName: string;
  optionDetail: string;
  price: number;
  photoUrl: string;
}

export interface apiResponse<T> {
  code: number;
  data: T;
}

export interface trimModelInterface {
  carOptionId: number;
  optionName: string;
  price: number;
  photos: [
    {
      content: string;
      photoSvgUrl: string;
      photoPngUrl: string;
    },
  ];
}

export interface optionInfoInterface {
  carOptionId: number;
  optionName: string;
  optionDetail: string;
}
