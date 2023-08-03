import CarModel from '../../components/common/CarModel';
import { Flex } from '../../components/common/Flex';
import TrimCard, { TrimCardInterface } from '../../components/myCar/TrimCard';

const Trim = () => {
  return (
    <Flex direction="column" justify="space-between">
      <CarModel />
      <Flex gap={24}>
        {trimInfo.map((trim: TrimCardInterface, idx: number) => (
          <TrimCard trim={trim} isSelected={isSelectedArr[idx]} />
        ))}
      </Flex>
    </Flex>
  );
};

export default Trim;

const trimInfo = [
  {
    name: 'Le Blanc(르블랑)',
    svg: [
      {
        path: '/',
        name: `20인치 알로이 휠`,
      },
      { path: '/', name: '서라운드 뷰 모니터' },
      { path: '/', name: '클러스터 ( 12.3 인치 컬러 LCD )' },
    ],
    price: 47720000,
  },
  {
    name: 'Le Blanc(르블랑)',
    svg: [
      { path: '/', name: '20인치 알로이 휠' },
      { path: '/', name: '서라운드 뷰 모니터' },
      { path: '/', name: '클러스터 ( 12.3 인치 컬러 LCD )' },
    ],
    price: 47720000,
  },
  {
    name: 'Le Blanc(르블랑)',
    svg: [
      { path: '/', name: '20인치 알로이 휠' },
      { path: '/', name: '서라운드 뷰 모니터' },
      { path: '/', name: '클러스터 ( 12.3 인치 컬러 LCD )' },
    ],
    price: 47720000,
  },
  {
    name: 'Le Blanc(르블랑)',
    svg: [
      { path: '/', name: '20인치 알로이 휠' },
      { path: '/', name: '서라운드 뷰 모니터' },
      { path: '/', name: '클러스터 ( 12.3 인치 컬러 LCD )' },
    ],
    price: 47720000,
  },
] as TrimCardInterface[];

const isSelectedArr = [true, false, false, false] as boolean[];
