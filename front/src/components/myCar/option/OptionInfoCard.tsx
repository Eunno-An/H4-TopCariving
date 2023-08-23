import { Flex, Tag, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import leftBtn from '@assets/images/leftBtn.svg';
import rightBtn from '@assets/images/rightBtn.svg';
import { optionInfoInterface } from '@interface/index';
import { css } from '@emotion/react';
import { useSuspenseFetch } from '@hooks/useSuspenseFetch';
import { OptionUrl } from '@utils/api';
import { useEffect, useState } from 'react';

export const OptionInfoCard = ({
  optionId,
  setSubInfoImage,
}: {
  optionId: number;
  setSubInfoImage: (imgSrc: string) => void;
}) => {
  const info = useSuspenseFetch(
    `${OptionUrl.DETAIL}/${optionId}`,
  ) as optionInfoInterface;

  const [cardPageIdx, setCarPageIdx] = useState(0);
  const [optionName, setOptionName] = useState('');
  const [optionDetail, setOptionDetail] = useState('');

  const onMovePage = (
    pageDirection: number,
    len = info.details?.length || 1,
  ) => {
    if (pageDirection === -1) {
      if (cardPageIdx - 1 < 0) {
        setCarPageIdx(len - 1);
      } else {
        setCarPageIdx(cardPageIdx - 1);
      }
    } else {
      setCarPageIdx((cardPageIdx + 1) % len);
    }
  };

  useEffect(() => {
    setCarPageIdx(0);
    setOptionName(info.details[0] ? info.details[0].optionName : '');
    setOptionDetail(info.details[0] ? info.details[0].optionDetail : '');
    setSubInfoImage(info.photoUrl);
  }, [info]);

  useEffect(() => {
    const currentDetailInfo = info.details[cardPageIdx];

    if (currentDetailInfo) {
      setSubInfoImage(currentDetailInfo.photoUrl);
      setOptionDetail(currentDetailInfo.optionDetail);
      setOptionName(currentDetailInfo.optionName);
    } else {
      setSubInfoImage(info.photoUrl);
      setOptionDetail('');
      setOptionName('');
    }
  }, [cardPageIdx]);

  const total = info.details.length;

  return (
    <Flex direction="column" justify="flex-start" gap={10}>
      <Flex gap={4} height="auto" justify="flex-start" css={TagWrap}>
        {info?.tags &&
          info.tags.map((it, idx) => (
            <Tag key={`tags_${idx}`} desc={it.tagContent} />
          ))}
      </Flex>
      <InfoContainer>
        {info.details.length ? (
          <>
            <InfoTitleContainer height={59} isContent={info.details.length}>
              <Flex justify="flex-start" gap={8}>
                <Flex
                  backgroundColor="Primary"
                  borderRadius="50%"
                  width={22}
                  height={22}
                  padding="6px"
                >
                  <Text palette="LightSand">{cardPageIdx + 1}</Text>
                </Flex>

                <Text palette="Primary">
                  {optionName}
                  {/* {info.details.length > cardPageIdx &&
                    info.details[cardPageIdx].optionName} */}
                </Text>
              </Flex>

              <Flex
                justify="flex-end"
                backgroundColor="DarkGray"
                borderRadius="13px"
                width="auto"
                padding="0 9px"
              >
                <Text palette="LightSand" typo="Caption_Regular">
                  {cardPageIdx + 1}/{total}
                </Text>
              </Flex>
            </InfoTitleContainer>

            <DetatilInfoContainer justify="space-between">
              <Text typo="Body3_Regular" palette="Primary">
                {/* {info.details.length > cardPageIdx &&
                  info.details[cardPageIdx].optionDetail} */}
                {optionDetail}
              </Text>
            </DetatilInfoContainer>

            <LeftButtonContainer onClick={() => onMovePage(-1)}>
              <img src={leftBtn} alt="" />
            </LeftButtonContainer>
            <RightButtonContainer onClick={() => onMovePage(1)}>
              <img src={rightBtn} alt="" />
            </RightButtonContainer>
          </>
        ) : (
          <Text palette="Primary" typo="Body2_Medium">
            하위옵션이 존재하지 않아요
          </Text>
        )}
      </InfoContainer>
    </Flex>
  );
};

const TagWrap = css`
  flex-wrap: wrap;
`;

const LeftButtonContainer = styled.div`
  position: absolute;
  cursor: pointer;

  top: 80px;
  right: 457px;
`;
const RightButtonContainer = styled.div`
  position: absolute;
  cursor: pointer;

  top: 80px;
  right: -3px;
`;

const DetatilInfoContainer = styled(Flex)`
  padding: 15px;
  align-items: flex-start;

  overflow: scroll;

  width: 400px;
  ::-webkit-scrollbar {
    display: none;
  }
`;

const InfoTitleContainer = styled(Flex)<{ isContent: number }>`
  justify-content: space-between;

  border-bottom: ${({ isContent }) =>
    isContent ? `2px solid ${theme.palette.Primary}` : `none`};

  width: 443px;
  height: auto;

  padding: 20px 0 15px 0;
`;

const InfoContainer = styled(Flex)`
  position: relative;
  flex-direction: column;
  width: 507px;
  height: 152px;

  border: 2px solid ${theme.palette.Primary};

  border-radius: 8px;

  background-color: rgba(0, 44, 95, 0.1);
`;
