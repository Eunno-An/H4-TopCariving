import styled from '@emotion/styled';
import { Flex } from '../common/Flex';
import { Text } from '../common/Text';
import { theme } from '../../styles/theme';

export interface TrimCardInterface {
  id: number;
  name: string;
  price: number;
}

const TrimCard = ({
  trim,
  isSelected,
}: {
  trim: TrimCardInterface;
  isSelected: boolean;
}) => {
  const { id, name, price } = trim;

  return (
    <CustomFlex
      direction="column"
      width={242}
      height={251}
      borderRadius="8px"
      padding={isSelected ? '9px' : '11px'}
      gap={16}
      backgroundColor="LightSand"
      isSelected={isSelected === true}
    >
      <Text typo="Heading3_Bold" palette={isSelected ? 'Primary' : 'Black'}>
        {name}
      </Text>
      <img src="/image/page/myCar/rowLine.svg" />
      <Flex justify="space-between">
        {trimOption[id].map((item, key) => (
          <Flex
            direction="column"
            justify="center"
            align="center"
            key={`trimCard_${key}`}
            gap={4}
          >
            <img src={item.svg} />
            <Flex direction="column">
              {item.name.map((name: string, key: number) => (
                <Text
                  typo="Caption_Medium"
                  palette={isSelected ? 'Primary' : 'DarkGray'}
                  key={`imgOption_${key}`}
                >
                  {name}
                </Text>
              ))}
            </Flex>
          </Flex>
        ))}
      </Flex>
      <img src="/image/page/myCar/rowLine.svg" />
      <Text typo="Heading3_Bold" palette={isSelected ? 'Primary' : 'Black'}>
        {`${price.toLocaleString('ko-KR')}원`}
      </Text>
    </CustomFlex>
  );
};

export default TrimCard;
type TrimOption = {
  [key: string]: {
    svg: string;
    name: string[];
  }[];
};

const trimOption = {
  1: [
    { svg: '/image/option/leBlanc1.svg', name: ['20인치', '알로이 휠'] },
    { svg: '/image/option/leBlanc2.svg', name: ['서라운드 뷰', '모니터'] },
    {
      svg: '/image/option/leBlanc3.svg',
      name: ['클러스터 ( 12.3', '인치 컬러 LCD )'],
    },
  ],
  2: [
    { svg: '/image/option/exclusive1.svg', name: ['12인치', '내비게이션'] },
    {
      svg: '/image/option/exclusive2.svg',
      name: ['네비게이션 기반', '스마트 크루즈', '컨트롤'],
    },
    {
      svg: '/image/option/exclusive3.svg',
      name: ['베젤리스', '인사이드 미러'],
    },
  ],
  3: [
    { svg: '/image/option/prestige1.svg', name: ['2열 수동식', '도어 커튼'] },
    { svg: '/image/option/prestige2.svg', name: ['스마트', '자세제어'] },
    {
      svg: '/image/option/prestige3.svg',
      name: ['전후식 통합', '터치 공조 컨트롤'],
    },
  ],
  4: [
    {
      svg: '/image/option/calligraphy1.svg',
      name: ['KRELL', '프리미어 사운드'],
    },
    {
      svg: '/image/option/calligraphy2.svg',
      name: ['원격 스마트', '주차보조'],
    },
    {
      svg: '/image/option/calligraphy3.svg',
      name: ['캘리그래피', '전용 디자인'],
    },
  ],
} as TrimOption;

const CustomFlex = styled(Flex)<{ isSelected: boolean }>`
  background-color: ${({ isSelected }) =>
    isSelected ? 'rgba(0, 44, 95, 0.10)' : theme.palette.LightSand};
  border: ${({ isSelected }) =>
    isSelected ? `2px solid ${theme.palette.Primary}` : ''};
  box-sizing: border-box;
  cursor: pointer;
`;
