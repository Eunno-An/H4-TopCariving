import styled from '@emotion/styled';
import { Flex, Text } from '@components/common';
import { Dispatch, SetStateAction, useState } from 'react';
import { useOutletContext } from 'react-router-dom';
import { myCarFooterInterface } from '@interface/index';
import { EngineCard } from '@components/myCar/trim';

export interface engineOptionInterface {
  engineType: string;
  price: number;
  description: string;
  maximumPower: string;
  maximumTorque: string;
  height: number;
}

const engineDummyData = [
  {
    engineType: '디젤 2.2',
    price: 1480000,
    description:
      '높은 토크로 파워풀한 드라이빙이 가능하며, 차급대비 연비 효율이 우수합니다.',
    maximumPower: '202/3,800PS/rpm',
    maximumTorque: '45.0/1,750~2,750kgf-m/rpm',
    img: 'https://topcariving.s3.ap-northeast-2.amazonaws.com/power/diesel.jpg',
    height: 218,
  },
  {
    engineType: '가솔린 3.8',
    price: 2150000,
    description:
      '고마력의 우수한 가속 성능을 확보하여, 넉넉하고 안정감 있는 주행이 가능합니다엔진의 진동이 적어 편안하고 조용한 드라이빙 감성을 제공합니다.',
    maximumPower: '295/6,000PS/rpm',
    maximumTorque: '36.2/5,200kgf-m/rpm',
    img: 'https://topcariving.s3.ap-northeast-2.amazonaws.com/power/gasoline.jpg',
    height: 242,
  },
];

const Engine = () => {
  const { footerInfo, setFooterInfo } = useOutletContext<{
    footerInfo: myCarFooterInterface;
    setFooterInfo: Dispatch<SetStateAction<myCarFooterInterface>>;
  }>();

  const [isSelected, setIsSelected] = useState(0);

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [initialFooterPrice, _] = useState(footerInfo.price);
  const onSelectEngine = (idx: number) => {
    setIsSelected(idx);

    const newTrimOption = footerInfo.name[1].split('/');
    newTrimOption[0] = engineDummyData[idx].engineType;

    setFooterInfo({
      ...footerInfo,
      price: initialFooterPrice + engineDummyData[idx].price,
      name: [footerInfo.name[0], newTrimOption.join('/')],
    });
  };

  return (
    <Flex gap={28} padding="28px 0 0 0" align="flex-start">
      <Flex direction="column" gap={23} height="auto">
        <Flex width={620} align="flex-start">
          <ImgTag src={engineDummyData[isSelected].img} alt="" />
        </Flex>
        <Flex width={620} direction="column" justify="space-between">
          <InfoBox justify="space-between" align="flex-start" height={48}>
            <Text typo="Heading1_Bold">
              {engineDummyData[isSelected].engineType}
            </Text>
            <Text typo="Heading2_Bold">
              +{engineDummyData[isSelected].price.toLocaleString('ko-KR')}원
            </Text>
          </InfoBox>
        </Flex>
      </Flex>

      <Flex direction="column" justify="flex-start" gap={12} height="auto">
        {engineDummyData.map((engine, idx) => (
          <div
            key={`engineOption_${idx}`}
            onClick={() => {
              onSelectEngine(idx);
            }}
          >
            <EngineCard engine={engine} isSelected={isSelected === idx} />
          </div>
        ))}
      </Flex>
    </Flex>
  );
};

export const InfoBox = styled(Flex)`
  border-bottom: 4px solid #545454;
`;

export const ImgTag = styled.img`
  width: 620px;
  height: 397px;
  border-radius: 8px;
`;

export default Engine;
