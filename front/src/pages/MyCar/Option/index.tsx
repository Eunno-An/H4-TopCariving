import { Flex, Text } from '@components/common';
import { Tag } from '@components/common/Tag';
import {
  OptionModal,
  alertContentInterface,
} from '@components/myCar/option/OptionModal';
import { useMyCar } from '@contexts/MyCarContext';
import { OptionCard, OptionInfoCard } from '@components/myCar/option';

import React, { useEffect, useState } from 'react';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { css } from '@emotion/react';
import vector478 from '@assets/images/Vector 478.svg';
import { useLoaderData } from 'react-router-dom';
import { apiInstance } from '@utils/api';
import { optionInfoInterface } from '@interface/index';

const defaultCategoryList = [
  '파워트레인/성능',
  '지능형 안전기술',
  '안전',
  '외관',
  '내장',
  '시트',
  '편의',
  '멀티미디어',
];

const cateName = {
  select: '선택항목',
  default: '기본 포함 품목',
};

export const MyCarOptions = () => {
  const { selectOptionData, defaultOptionData } =
    useLoaderData() as optionDatasInterface;

  const { myCarInfo, setMyCarInfo } = useMyCar();
  const [dummyData, setDummyData] = useState(selectOptionData);

  const [selectedItem, setSelectedItem] = useState(0);
  const [selectedMenu, setSelectedMenu] = useState(cateName.select);
  const [defaultOption, setDefaultOption] = useState(0);
  const [info, setInfo] = useState<optionInfoInterface>();

  const [cardPageIdx, setCarPageIdx] = useState(0);
  const [isOpen, setIsOpen] = useState(false);

  const [currentDefaultCategory, setCurrentDefaultCategory] =
    useState<string>('파워트레인/성능');

  const [modalData, setModalData] = useState<alertContentInterface>({
    title: '',
    imgSrc: '',
    desc: '',
  });

  const getSelectedState = (optionIdx: number) => {
    for (const selectedOption of myCarInfo.selectedOption) {
      if (selectedOption.id === dummyData[optionIdx].carOptionId) {
        return true;
      }
    }
    return false;
  };

  const changeUserOptionList = (optionIdx: number) => {
    getSelectedState(optionIdx)
      ? setMyCarInfo({
          ...myCarInfo,
          price: myCarInfo.price - dummyData[optionIdx].price,
          selectedOption: myCarInfo.selectedOption.filter(
            (item) => item.id !== dummyData[optionIdx].carOptionId,
          ),
        })
      : setMyCarInfo({
          ...myCarInfo,
          price: myCarInfo.price + dummyData[optionIdx].price,
          selectedOption: [
            ...myCarInfo.selectedOption,
            {
              id: dummyData[optionIdx].carOptionId,
              name: dummyData[optionIdx].optionName,
              price: dummyData[optionIdx].price,
            },
          ],
        });
  };

  const onMovePage = ({ pageParam }: { pageParam: number }) => {
    const len = info?.details?.length ? info?.details?.length : 1;

    if (pageParam === -1) {
      if (cardPageIdx - 1 < 0) {
        setCarPageIdx(len - 1);
      } else {
        setCarPageIdx(cardPageIdx - 1);
      }
    } else {
      setCarPageIdx((cardPageIdx + 1) % len);
    }
  };

  const onSelectedItemHandler = (idx: number) => {
    setCarPageIdx(0);

    if (selectedMenu === cateName.select) {
      selectedMenu === cateName.select && setSelectedItem(idx);
    } else {
      onModalHandler(idx); // 기본 포함 품목일때 모달창 띄우기
    }
  };

  const onModalHandler = (idx: number) => {
    const defaultOption = defaultOptionData[currentDefaultCategory][idx];
    const modalOptionData: alertContentInterface = {
      title: defaultOption.optionName,
      imgSrc: defaultOption.photoUrl,
      desc: defaultOption?.optionDetail
        ? defaultOption.optionDetail
        : '상세설명이 없습니다.',
    };

    setIsOpen(true);
    setModalData({ ...modalOptionData });
  };

  const modalCloseHandler = () => {
    setIsOpen(false);
  };

  const fetchDetilsData = async (optionId: number) => {
    const res = (await await apiInstance({
      url: `/api/options/details/${optionId}`,
      method: 'GET',
    })) as optionInfoInterface;

    return res;
  };

  useEffect(() => {
    async function fetchData() {
      const data = (await fetchDetilsData(
        dummyData[selectedItem].carOptionId,
      )) as optionInfoInterface;

      setInfo(data);
    }
    fetchData();
  }, [selectedItem]);

  return (
    <Flex direction="column" justify="flex-start" height={561} gap={15}>
      {/* 옵션 상단 */}
      <Flex gap={39}>
        {/* 이미지 */}
        <Flex width={479} height={304}>
          <ImgContainer src={dummyData[selectedItem].photoUrl} alt="" />
        </Flex>
        {/* 옵션 Info */}
        <Flex direction="column">
          {/* 옵션 이름 / 가격 */}
          <OptionContainer>
            <Text typo="Heading1_Bold">
              {dummyData[selectedItem].optionName}
            </Text>
            <Text typo="Heading2_Bold">
              +{dummyData[selectedItem].price.toLocaleString()} 원
            </Text>
          </OptionContainer>
          {/* 옵션에대한 태그칩 */}
          <Flex
            width={507}
            height={108}
            padding="10px 0 10px 0"
            direction="column"
            align="flex-start"
          >
            <Text>
              {dummyData[selectedItem].optionName}
              <Text typo="Body3_Regular">
                에 대해 시승자들은 이런 후기를 남겼어요
              </Text>
            </Text>
            <Flex gap={4} height={70} justify="flex-start" css={TagWrap}>
              {info?.tags &&
                info.tags.map((it, idx) => (
                  <Tag key={`tags_${idx}`} desc={it.tagContent} />
                ))}
            </Flex>
          </Flex>
          {/* 옵션 세부 설명 */}
          {info && (
            <OptionInfoCard
              info={info}
              onMovePage={onMovePage}
              cardPageIdx={cardPageIdx}
            />
          )}
        </Flex>
      </Flex>

      {/* 옵션 하단 */}
      <Flex direction="column" height={245} gap={20}>
        {/* 선택 항목 / 기본 포함 항목 */}
        <OptionMenu justify="flex-start" height={40} gap={23}>
          <MenuName
            isSelected={selectedMenu === cateName.select}
            onClick={() => {
              if (selectedMenu !== cateName.select) {
                setCarPageIdx(0);
                setDummyData(dummyData);
                setSelectedMenu(cateName.select);
              }
            }}
          >
            선택항목
          </MenuName>
          <MenuName
            isSelected={selectedMenu === cateName.default}
            onClick={() => {
              if (selectedMenu !== cateName.default) {
                setCarPageIdx(0);
                setSelectedMenu(cateName.default);
              }
            }}
          >
            기본 포함 품목
          </MenuName>
        </OptionMenu>
        {/* 기본 포함 품목 카테고리 */}
        {selectedMenu !== cateName.select && (
          <Flex justify="flex-start" gap={10} height={10}>
            {defaultCategoryList.map((it, idx) => (
              <React.Fragment key={`optionTag_${idx}`}>
                <OptionTag
                  typo="Body4_Medium"
                  palette={defaultOption === idx ? 'Black' : 'MediumGray'}
                  onClick={() => {
                    setCurrentDefaultCategory(defaultCategoryList[idx]);
                    setDefaultOption(idx);
                  }}
                >
                  {it}
                </OptionTag>
                {idx !== 7 && <img src={vector478} alt="" />}
              </React.Fragment>
            ))}
          </Flex>
        )}
        {/* 옵션 카드 */}
        <OptionListContainer
          justify="flex-start"
          align="flex-end"
          gap={6}
          height={300}
        >
          {(selectedMenu === cateName.select
            ? dummyData
            : defaultOptionData[currentDefaultCategory]
          ).map((item, idx) => (
            <div
              key={`optionCard_${item.carOptionId}`}
              onClick={() => onSelectedItemHandler(idx)}
            >
              <OptionCard
                idx={idx}
                isSelected={myCarInfo.selectedOption.some(
                  (item) => item.id === dummyData[idx].carOptionId,
                )}
                dimData={
                  dummyData[idx]?.optionDetail
                    ? dummyData[idx].optionDetail
                    : '상세설명이 없습니다.'
                }
                optionItem={item}
                selectedMenu={selectedMenu}
                changeUserOptionList={changeUserOptionList}
              />
            </div>
          ))}
        </OptionListContainer>
      </Flex>
      {isOpen && (
        <OptionModal
          content={modalData}
          buttonInfo={[
            {
              text: '확인',
              color: 'Primary',
              onClick: modalCloseHandler,
            },
          ]}
        />
      )}
    </Flex>
  );
};

const OptionListContainer = styled(Flex)`
  overflow-y: scroll;

  ::-webkit-scrollbar {
    display: none;
  }
`;

const OptionTag = styled(Text)`
  cursor: pointer;
`;

const MenuName = styled(Text)<{ isSelected: boolean }>`
  ${({ isSelected }) =>
    isSelected ? theme.typo.Body1_Medium : theme.typo.Heading4_Bold};
  color: ${({ isSelected }) => !isSelected && theme.palette.LightGray};
  cursor: pointer;
`;

const TagWrap = css`
  flex-wrap: wrap;
`;

const OptionMenu = styled(Flex)`
  border-bottom: 3px solid ${theme.palette.LightGray};
`;

const OptionContainer = styled(Flex)`
  width: 507px;
  height: 44px;
  justify-content: space-between;

  border-bottom: 2px solid #545454;
`;

const ImgContainer = styled.img`
  width: 479px;
  height: 304px;
  border-radius: 4px;
`;

interface optionDatasInterface {
  selectOptionData: selectOptionInterface[];
  defaultOptionData: defaultOptionInterface;
}

export interface urlPathInterface {
  hash: string;
  key: string;
  pathname: string;
  search: string;
  state: string | null;
}

export interface selectOptionInterface {
  carOptionId: number;
  optionName: string;
  optionDetail: string;
  price: number;
  photoUrl: string;
}

export interface optionDetailInterface {
  carOptionId: number;
  optionName: string;
  optionDetail: string;
}

export interface optionTagInterface {
  tagContent: string;
}

export interface optionItemInterface {
  carOptionId: number;
  optionName: string;
  optionDetail?: string;
  price: number;
  photoUrl: string;
}

export interface defaultOptionInterface {
  [key: string]: optionItemInterface[];
}

export interface detailsOptioinInterface {
  carOptionId: number;
  optionName: string;
  price: number;
  photoUrl: string;
  details: string[];
  tags: tagInterface[];
}

export interface tagInterface {
  tagContent: string;
}
