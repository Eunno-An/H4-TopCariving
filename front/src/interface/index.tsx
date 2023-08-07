export interface myCarFooterInterface {
  name: string[];
  color: string[];
  option: { outer: string; inner: string };
  price: number;
}

export interface myCarOptionInterface {
  carOptionId: number;
  optionName: string;
  optionDetail: string;
  price: number;
  photoUrl: string;
}
