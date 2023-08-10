export type colorEng =
  | '어비스블랙펄'
  | '쉬머링 실버 메탈릭'
  | '문라이프 블루 펄'
  | '가이아 브라운 펄'
  | '그라파이트 그레이 메탈릭'
  | '크리미 화이트 펄';

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
