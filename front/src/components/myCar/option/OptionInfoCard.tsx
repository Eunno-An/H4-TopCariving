import { Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import leftBtn from '@assets/images/leftBtn.svg';
import rightBtn from '@assets/images/rightBtn.svg';
import { optionInfoInterface } from '@interface/index';

export const OptionInfoCard = ({
  info,
  onMovePage,
  cardPageIdx,
}: {
  info: optionInfoInterface;
  onMovePage: ({ pageParam }: { pageParam: number }) => void;
  cardPageIdx: number;
}) => {
  const total = info.details?.length;

  return (
    <InfoContainer>
      {info.details?.length ? (
        <>
          <InfoTitleContainer height={59} isContent={info.details?.length}>
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
                {info.details[cardPageIdx].optionName}
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
              {info.details[cardPageIdx].optionDetail}
            </Text>
          </DetatilInfoContainer>

          <LeftButtonContainer onClick={() => onMovePage({ pageParam: -1 })}>
            <img src={leftBtn} alt="" />
          </LeftButtonContainer>
          <RightButtonContainer onClick={() => onMovePage({ pageParam: +1 })}>
            <img src={rightBtn} alt="" />
          </RightButtonContainer>
        </>
      ) : (
        <Text palette="Primary" typo="Body2_Medium">
          하위옵션이 존재하지 않아요
        </Text>
      )}
    </InfoContainer>
  );
};

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
