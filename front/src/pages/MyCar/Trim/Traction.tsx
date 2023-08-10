import { Flex, Text } from '@components/common';
import { Dispatch, SetStateAction, useState } from 'react';
import { ImgTag, InfoBox } from './Engine';
import { myCarFooterInterface, myCarOptionInterface } from '@interface/index';
import { useOutletContext } from 'react-router-dom';
import { BodyCard } from '@components/myCar/trim';

const bodyTypeInfo = [
  {
    carOptionId: 1,
    optionName: '2WD',
    optionDetail:
      '엔진에서 전달되는 동력이 전/후륜 바퀴 중 한쪽으로만 전달되어 차량을 움직이는 방식입니다차체가 가벼워 연료 효율이 높습니다',
    price: 0,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/driving_method/2wd.jpg',
  },
  {
    carOptionId: 1,
    optionName: '4WD',
    optionDetail:
      '전자식 상시 4륜 구동 시스템 입니다도로의 상황이나 주행 환경에 맞춰 전후륜 구동력을 자동배분하여 주행 안전성을 높여줍니다',
    price: 0,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/driving_method/4wd.jpg',
  },
] as myCarOptionInterface[];

const Traction = () => {
  const { footerInfo, setFooterInfo } = useOutletContext<{
    footerInfo: myCarFooterInterface;
    setFooterInfo: Dispatch<SetStateAction<myCarFooterInterface>>;
  }>();

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [initialFooterPrice, _] = useState(footerInfo.price);

  const [isSelected, setIsSelected] = useState(0);

  const onSelectBodyType = (idx: number) => {
    setIsSelected(idx);

    const newTrimOption = footerInfo.name[1].split('/');
    newTrimOption[2] = bodyTypeInfo[idx].optionName;

    setFooterInfo({
      ...footerInfo,
      name: [footerInfo.name[0], newTrimOption.join('/')],
      price: initialFooterPrice + bodyTypeInfo[idx].price,
    });
  };

  return (
    <Flex padding="28px 0 0 0" align="flex-start" gap={28}>
      <Flex direction="column" height="auto" gap={23}>
        <Flex width={620} align="flex-start">
          <ImgTag src={bodyTypeInfo[isSelected].photoUrl} alt="" />
        </Flex>
        <Flex width={620} direction="column" justify="space-between">
          <InfoBox justify="space-between" align="flex-start" height={48}>
            <Text typo="Heading1_Bold">
              {bodyTypeInfo[isSelected].optionName}
            </Text>
            <Text typo="Heading2_Bold">
              +{bodyTypeInfo[isSelected].price.toLocaleString('ko-KR')}원
            </Text>
          </InfoBox>
        </Flex>
      </Flex>

      <Flex direction="column" justify="flex-start" gap={12}>
        {bodyTypeInfo.map((body, idx) => (
          <div
            key={`engineOption_${idx}`}
            onClick={() => {
              onSelectBodyType(idx);
            }}
          >
            <BodyCard option={body} isSelected={isSelected === idx} />
          </div>
        ))}
      </Flex>
    </Flex>
  );
};

export default Traction;
