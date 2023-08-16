import {
  Dispatch,
  ReactNode,
  SetStateAction,
  createContext,
  useContext,
  useState,
} from 'react';

export type exteriorColorType =
  | '어비스블랙펄'
  | '쉬머링 실버 메탈릭'
  | '문라이프 블루 펄'
  | '가이아 브라운 펄'
  | '그라파이트 그레이 메탈릭'
  | '크리미 화이트 펄';

export type trimKey = 'type' | 'engine' | 'bodyType' | 'traction';
export type optionKey = 'selected' | 'genuine' | 'performance';

export interface MyCarInfoInterface {
  trim: {
    type: { id: number; name: string; price: number } | null;
    engine: { id: number; name: string; price: number } | null;
    bodyType: { id: number; name: string; price: number } | null;
    traction: { id: number; name: string; price: number } | null;
    [key: string]: { id: number; name: string; price: number } | null;
  };
  color: {
    exteriorColor: {
      id: number;
      name: exteriorColorType;
      url: string;
      price: number;
    } | null;
    interiorColor: {
      id: number;
      name: string;
      url: string;
      price: number;
    } | null;
  };
  option: { [key in optionKey]: { id: number; name: string; price: number }[] };
  price: number;
}

export interface MyCarContextType {
  myCarInfo: MyCarInfoInterface;
  setMyCarInfo: Dispatch<SetStateAction<MyCarInfoInterface>>;
}

export const initMyCarInfo = {
  trim: { type: null, engine: null, bodyType: null, traction: null },
  color: { exteriorColor: null, interiorColor: null },
  option: { selected: [], genuine: [], performance: [] },
  price: 0,
};

const MyCarContext = createContext<MyCarContextType | null>(null);

export function useMyCar() {
  const context = useContext(MyCarContext);
  if (context === null) {
    throw new Error('useMyCar must be used within an MyCarProvider');
  }
  return context;
}

export function MyCarProvider({ children }: { children: ReactNode }) {
  const localMyCarInfo = localStorage.getItem('myCarInfo');
  const [myCarInfo, setMyCarInfo] = useState<MyCarInfoInterface>(
    localMyCarInfo ? JSON.parse(localMyCarInfo) : initMyCarInfo,
  );

  const value = {
    myCarInfo,
    setMyCarInfo,
  } as MyCarContextType;

  return (
    <MyCarContext.Provider value={value}> {children}</MyCarContext.Provider>
  );
}
