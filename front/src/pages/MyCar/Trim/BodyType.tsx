import { Flex, Text } from '@components/common';
import { useEffect, useState } from 'react';
import { ImgTag, InfoBox } from './Engine';
import { myCarOptionInterface } from '@interface/index';
import { BodyCard } from '@components/myCar/trim';
import { useMyCar } from '@contexts/MyCarContext';

const bodyTypeInfo = [
  {
    carOptionId: 1,
    optionName: '7인승',
    optionDetail:
      '기존 8인승 시트(1열 2명, 2열 3명, 3열 3명)에서 2열 가운데 시트를 없애 2열 탑승객의 편의는 물론, 3열 탑승객의 승하차가 편리합니다.',
    price: 200000,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/body_type/seven.jpg',
  },
  {
    carOptionId: 1,
    optionName: '8인승',
    optionDetail:
      '1열 2명, 2열 3명, 3열 3명이 탑승할 수 있는 구조로, 많은 인원이 탑승할 수 있도록 배려하였습니다',
    price: 0,
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/body_type/eight.jpg',
  },
] as myCarOptionInterface[];

const BodyType = () => {
  const { myCarInfo, setMyCarInfo } = useMyCar();
  const [isSelected, setIsSelected] = useState(0);

  useEffect(() => {
    const getData = async () => {
      if (bodyTypeInfo) {
        if (myCarInfo.trim.bodyType === null) {
          setMyCarInfo({
            ...myCarInfo,
            price: myCarInfo.price + bodyTypeInfo[0].price,
            trim: {
              ...myCarInfo.trim,
              bodyType: {
                id: bodyTypeInfo[0].carOptionId,
                name: bodyTypeInfo[0].optionName,
              },
            },
          });
        } else {
          bodyTypeInfo.forEach((bodyType, selectIdx) => {
            if (bodyType.carOptionId === myCarInfo.trim.bodyType?.id) {
              setIsSelected(selectIdx);
            }
          });
        }
      }
    };

    getData();
  }, []);

  const onSelectBodyType = (idx: number) => {
    bodyTypeInfo &&
      setMyCarInfo({
        ...myCarInfo,
        trim: {
          ...myCarInfo.trim,
          bodyType: {
            id: bodyTypeInfo[idx].carOptionId,
            name: bodyTypeInfo[idx].optionName,
          },
        },
        price:
          myCarInfo.price -
          bodyTypeInfo[isSelected].price +
          bodyTypeInfo[idx].price,
      });
    setIsSelected(idx);
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

export default BodyType;
