import { Button, Flex, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import bookmark from '@assets/images/bookmark.svg';
export const ArchiveShortInfo = () => {
  return (
    <Flex backgroundColor="White">
      <Flex
        direction="column"
        width={1024}
        padding="16px 0 0 0"
        justify="flex-start"
        gap={47}
      >
        <Flex direction="column" align="flex-start" height="auto">
          <Text typo="Body1_Regular">총 가격</Text>
          <Text typo="Heading1_Bold">47,720,000원</Text>
        </Flex>
        <Flex height="auto" justify="space-between">
          <Flex direction="column" height={99} gap={9}>
            <Flex justify="flex-start">
              <Text typo="Body3_Medium" palette="DarkGray">
                선택옵션
              </Text>
            </Flex>
            <OptionTagContainer
              gap={8}
              width={546}
              justify="flex-start"
              height="auto"
            >
              <OptionTag>
                <Text typo="Body3_Regular" palette="DarkGray">
                  컴포트 || 패키지
                </Text>
              </OptionTag>
              <OptionTag>
                <Text typo="Body3_Regular" palette="DarkGray">
                  현대스마트센스 | 패키지
                </Text>
              </OptionTag>
              <OptionTag>
                <Text typo="Body3_Regular" palette="DarkGray">
                  2열 통풍시트
                </Text>
              </OptionTag>
              <OptionTag>
                <Text typo="Body3_Regular" palette="DarkGray">
                  빌트인 캠(보조배터리 포함)
                </Text>
              </OptionTag>
              <OptionTag>
                <Text typo="Body3_Regular" palette="DarkGray">
                  사이드스텝
                </Text>
              </OptionTag>
              <OptionTag>
                <Text typo="Body3_Regular" palette="DarkGray">
                  적외선 무릎워머
                </Text>
              </OptionTag>
            </OptionTagContainer>
          </Flex>
          <Flex gap={14}>
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

  padding: 10px 12px;

  border-radius: 4px;
  border: 0.5px solid ${theme.palette.LightGray};
`;
