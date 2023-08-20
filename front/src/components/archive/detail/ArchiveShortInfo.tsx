import { Button, Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import bookmark from '@assets/images/bookmark.svg';
import { ArchiveDetailPageProps } from '@pages/Archive/detail';

export const ArchiveShortInfo = ({
  detailInfo,
  optionDetail,
}: ArchiveDetailPageProps) => {
  return (
    <Flex direction="column" justify="flex-start">
      <Flex
        direction="column"
        width={1024}
        padding="50px 0 30px 0"
        justify="flex-start"
        gap={47}
      >
        <Flex direction="column" align="flex-start" height="auto">
          <Text typo="Body1_Regular">총 가격</Text>
          <Text typo="Heading1_Bold">{`${detailInfo?.totalPrice.toLocaleString()}원`}</Text>
        </Flex>
        <Flex height="auto" justify="space-between">
          <Flex
            direction="column"
            justify="flex-start"
            align="flex-start"
            height="auto"
            gap={9}
          >
            <Text typo="Body3_Medium" palette="DarkGray">
              선택옵션
            </Text>
            <OptionTagContainer
              gap={8}
              width={546}
              justify="flex-start"
              height="auto"
            >
              {optionDetail?.[`상세 품목`].map((option) => (
                <OptionTag>
                  <Text typo="Body3_Regular" palette="DarkGray">
                    {option.optionName}
                  </Text>
                </OptionTag>
              ))}
            </OptionTagContainer>
          </Flex>
          <Flex gap={14} height="auto">
            <BookMark>
              <img src={bookmark} alt="" />
            </BookMark>
            <Button
              backgroundColor="Primary"
              padding="16px 71px"
              typo="Heading4_Bold"
              heightType="large"
            >
              이 차량으로 내 차 만들기 시작
            </Button>
          </Flex>
        </Flex>
      </Flex>
      <Flex height={18} backgroundColor="LightSand"></Flex>
    </Flex>
  );
};

const BookMark = styled(Flex)`
  width: 52px;
  height: 52px;
  border-radius: 50%;
  background-color: ${theme.palette.Sand};
`;

const OptionTagContainer = styled(Flex)`
  flex-wrap: wrap;
`;

const OptionTag = styled(Flex)`
  width: auto;
  height: 22px;

  padding: 16px 12px;

  border-radius: 4px;
  border: 0.5px solid ${theme.palette.LightGray};
`;
