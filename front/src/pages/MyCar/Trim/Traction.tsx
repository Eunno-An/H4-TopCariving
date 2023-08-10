import { Flex, Text } from '@components/common';
import { useEffect, useState } from 'react';
import { ImgTag, InfoBox } from './Engine';
import { myCarOptionInterface } from '@interface/index';
import { BodyCard } from '@components/myCar/trim';
import { useMyCar } from '@contexts/MyCarContext';

const Traction = () => {
  const { myCarInfo, setMyCarInfo } = useMyCar();
  const [isSelected, setIsSelected] = useState(0);

  useEffect(() => {
    const getData = async () => {
      if (tractionInfo) {
        if (myCarInfo.trim.traction === null) {
          setMyCarInfo({
            ...myCarInfo,
            price: myCarInfo.price + tractionInfo[0].price,
            trim: {
              ...myCarInfo.trim,
              traction: {
                id: tractionInfo[0].carOptionId,
                name: tractionInfo[0].optionName,
              },
            },
          });
        } else {
          tractionInfo.forEach((traction, selectIdx) => {
            if (traction.carOptionId === myCarInfo.trim.traction?.id) {
              setIsSelected(selectIdx);
            }
          });
        }
      }
    };

    getData();
  }, []);

  const onSelectBodyType = (idx: number) => {
    setMyCarInfo({
      ...myCarInfo,
      trim: {
        ...myCarInfo.trim,
        traction: {
          id: tractionInfo[idx].carOptionId,
          name: tractionInfo[idx].optionName,
        },
      },
      price:
        myCarInfo.price -
        tractionInfo[isSelected].price +
        tractionInfo[idx].price,
    });
    setIsSelected(idx);
  };

  return (
    <Flex padding="28px 0 0 0" align="flex-start" gap={28}>
      <Flex direction="column" height="auto" gap={23}>
        <Flex width={620} align="flex-start">
          <ImgTag src={tractionInfo[isSelected].photoUrl} alt="" />
        </Flex>
        <Flex width={620} direction="column" justify="space-between">
          <InfoBox justify="space-between" align="flex-start" height={48}>
            <Text typo="Heading1_Bold">
              {tractionInfo[isSelected].optionName}
            </Text>
            <Text typo="Heading2_Bold">
              +{tractionInfo[isSelected].price.toLocaleString('ko-KR')}원
            </Text>
          </InfoBox>
        </Flex>
      </Flex>

      <Flex direction="column" justify="flex-start" gap={12}>
        {tractionInfo.map((body, idx) => (
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

const tractionInfo = [
  {
    carOptionId: 1,
    optionName: '2WD',
    optionDetail:
      '엔진에서 전달되는 동력이 전/후륜 바퀴 중 한쪽으로만 전달되어 차량을 움직이는 방식입니다차체가 가벼워 연료 효율이 높습니다',
    price: 1000000,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/driving_method/2wd.jpg',
  },
  {
    carOptionId: 1,
    optionName: '4WD',
    optionDetail:
      '전자식 상시 4륜 구동 시스템 입니다도로의 상황이나 주행 환경에 맞춰 전후륜 구동력을 자동배분하여 주행 안전성을 높여줍니다',
    price: 123456,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/driving_method/4wd.jpg',
  },
] as myCarOptionInterface[];
