import { CarModel, Flex, Tag, Text } from '@components/common';
import { ImgTag, InfoBox } from '../Trim/Engine';
import { useEffect, useState } from 'react';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import check from '@assets/images/blueCheck.svg';
import { exteriorColorType, useMyCar } from '@contexts/MyCarContext';
import { css } from '@emotion/react';

import {
  colorInfoInterface,
  colorKey,
  exteriorColorResponseInterface,
  interiorColorResponseInterface,
} from './interface';
import { useLoaderData } from 'react-router-dom';
import { ArchivePopup } from '@components/common/ArchivePopup';

export const colorPath = {
  어비스블랙펄: 'black',
  '쉬머링 실버 메탈릭': 'silver',
  '문라이프 블루 펄': 'blue',
  '가이아 브라운 펄': 'brown',
  '그라파이트 그레이 메탈릭': 'gray',
  '크리미 화이트 펄': 'white',
} as { [key in exteriorColorType]: string };

const Color = () => {
  const { myCarInfo, setMyCarInfo } = useMyCar();
  const colorInfo = useLoaderData() as colorInfoInterface;

  const [isLastClick, setIsLastClick] = useState<{
    key: keyof colorInfoInterface;
    idx: number;
  }>({
    key: 'exteriorColorResponses',
    idx: 0,
  });
  useEffect(() => {
    const getData = async () => {
      if (colorInfo) {
        if (myCarInfo.color.exteriorColor === null) {
          const [initExteriorColor, initInteriorColor] = [
            colorInfo.exteriorColorResponses[0],
            colorInfo.interiorColorResponses[0],
          ];
          setMyCarInfo({
            ...myCarInfo,
            price:
              myCarInfo.price +
              initExteriorColor.price +
              initInteriorColor.price,
            color: {
              exteriorColor: {
                id: initExteriorColor.carOptionId,
                name: initExteriorColor.optionName,
                url: initExteriorColor.colorUrl,
                price: initExteriorColor.price,
              },
              interiorColor: {
                id: initInteriorColor.carOptionId,
                name: initInteriorColor.optionName,
                url: initInteriorColor.colorUrl,
                price: initInteriorColor.price,
              },
            },
          });
        } else {
          const lastIdx = colorInfo.exteriorColorResponses.findIndex(
            (item) => item.carOptionId === myCarInfo.color.exteriorColor?.id,
          );
          setIsLastClick({
            key: 'exteriorColorResponses',
            idx: lastIdx,
          });
        }
      }
    };
    getData();
  }, []);

  const onClickColorBox = (colorKey: colorKey, idx: number) => {
    const changeColorKey =
      colorKey === 'exteriorColorResponses' ? 'exteriorColor' : 'interiorColor';

    let lastColorItem;
    if (colorKey === 'exteriorColorResponses') {
      lastColorItem = colorInfo[colorKey].find(
        (item: exteriorColorResponseInterface) =>
          item.carOptionId === myCarInfo.color[changeColorKey]?.id,
      );
    } else {
      lastColorItem = colorInfo[colorKey].find(
        (item: interiorColorResponseInterface) =>
          item.carOptionId === myCarInfo.color[changeColorKey]?.id,
      );
    }

    if (lastColorItem) {
      setMyCarInfo({
        ...myCarInfo,
        color: {
          ...myCarInfo.color,
          [changeColorKey]: {
            id: colorInfo[colorKey][idx].carOptionId,
            name: colorInfo[colorKey][idx].optionName,
            url: colorInfo[colorKey][idx].colorUrl,
            price: colorInfo[colorKey][idx].price,
          },
        },
        price:
          myCarInfo.price -
          lastColorItem.price +
          colorInfo[colorKey][idx].price,
      });
    }

    setIsLastClick({
      key: colorKey as colorKey,
      idx: idx,
    });
  };

  return (
    <>
      <Flex gap={28} padding="28px 0 0 0" align="flex-start">
        <Flex direction="column" gap={23} height="auto">
          <Flex width={620} height={397} align="center">
            {isLastClick.key === 'exteriorColorResponses' ? (
              <CarModel
                exteriorColor={
                  myCarInfo.color.exteriorColor
                    ? colorPath[myCarInfo.color.exteriorColor?.name]
                    : 'black'
                }
              />
            ) : (
              <ImgTag
                src={colorInfo[isLastClick.key][isLastClick.idx].photoUrl}
                alt=""
              />
            )}
          </Flex>
          <Flex width={620} direction="column" justify="space-between">
            <InfoBox justify="space-between" align="flex-start" height={48}>
              <Text typo="Heading1_Bold">
                {colorInfo[isLastClick.key][isLastClick.idx].optionName}
              </Text>
              <Text typo="Heading2_Bold">
                {`+${colorInfo[isLastClick.key][
                  isLastClick.idx
                ].price.toLocaleString()}원`}
              </Text>
            </InfoBox>
          </Flex>
          <Flex
            justify="flex-start"
            css={css`
              flex-wrap: wrap;
              gap: 4px;
            `}
          >
            {colorInfo[isLastClick.key][isLastClick.idx].tagResponses.map(
              (tag, idx) => (
                <Tag desc={tag.tagContent} key={`tag_${idx}`} />
              ),
            )}
          </Flex>
        </Flex>
        <Flex direction="column" justify="flex-start">
          <Flex
            direction="column"
            justify="flex-start"
            gap={12}
            width={331}
            height="auto"
          >
            <Flex justify="space-between">
              <Text typo="Heading4_Medium">외장 색상</Text>
              <Text typo="Body4_Regular">
                {myCarInfo.color.exteriorColor?.name}
              </Text>
            </Flex>

            <GridContainer>
              {colorInfo.exteriorColorResponses.map((item, idx) => (
                <Flex
                  direction="column"
                  justify="flex-start"
                  gap={8}
                  key={item.carOptionId}
                >
                  <ColorWrapper
                    isSelected={
                      myCarInfo.color.exteriorColor?.id === item.carOptionId
                    }
                  >
                    <ExteriorColor
                      src={item.colorUrl}
                      onClick={() => {
                        onClickColorBox('exteriorColorResponses', idx);
                      }}
                    />
                    <BlueCheck
                      src={check}
                      type={'exteriorColorResponses'}
                      isSelected={
                        myCarInfo.color.exteriorColor?.id === item.carOptionId
                      }
                    />
                  </ColorWrapper>
                  <Text typo="Body4_Regular">{item.optionName}</Text>
                </Flex>
              ))}
            </GridContainer>
          </Flex>
          <Flex
            direction="column"
            justify="flex-start"
            gap={12}
            width={331}
            height="auto"
          >
            <Flex justify="space-between">
              <Text typo="Heading4_Medium">내장 색상</Text>
              <Text typo="Body4_Regular">
                {myCarInfo.color.interiorColor?.name}
              </Text>
            </Flex>

            <Flex direction="column" gap={16}>
              {colorInfo.interiorColorResponses.map((item, idx) => (
                <ColorWrapper
                  key={`interiorCard_${idx}`}
                  isSelected={
                    myCarInfo.color.interiorColor?.id === item.carOptionId
                  }
                >
                  <InteriorColor
                    src={item.colorUrl}
                    onClick={() => {
                      onClickColorBox('interiorColorResponses', idx);
                    }}
                  />
                  <BlueCheck
                    src={check}
                    type={'interiorColorResponses'}
                    isSelected={
                      myCarInfo.color.interiorColor?.id === item.carOptionId
                    }
                  />
                </ColorWrapper>
              ))}
            </Flex>
          </Flex>
        </Flex>
      </Flex>
      <ArchivePopup desc={`다른 사람이 선택한 색상후기가 궁금하다면?`} />
    </>
  );
};

const GridContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 3개의 열, 각 열은 동일한 너비 */
  grid-template-rows: repeat(2, 1fr); /* 2개의 행, 각 행은 200px 높이 */
  gap: 11px; /* 열과 행 사이의 간격 */
`;

const ColorWrapper = styled.div<{ isSelected: boolean }>`
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  border: ${(props) =>
    props.isSelected
      ? `3px solid ${theme.palette.ActiveBlue}`
      : `3px solid ${theme.palette.White}`};

  border-radius: 8px;
  box-sizing: border-box;

  &:hover {
    border: 3px solid ${theme.palette.ActiveBlue};
  }
  transition: ease 0.3s;
`;

const ExteriorColor = styled.img`
  width: 89px;
  height: 89px;
  background: #f0f2f1;
  cursor: pointer;

  border-radius: 6px;
`;

const InteriorColor = styled.img`
  width: 100%;
  border-radius: 6px;
  cursor: pointer;
`;

const BlueCheck = styled.img<{ type: colorKey; isSelected: boolean }>`
  position: absolute;
  top: -11px;
  right: -11px;

  width: 22px;
  height: 22px;

  display: ${(props) => (props.isSelected ? 'block' : 'none')};
`;

export default Color;
