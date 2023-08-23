import { Flex, Tag, Text } from '@components/common';
import styled from '@emotion/styled';
import { theme } from '@styles/theme';
import { OptionChip } from '.';
import { css } from '@emotion/react';
import { archiveSearchResponsesInterface } from '@pages/Archive/main';
import { getDate } from '@utils/getDate';

interface ArchiveCardProps {
  archiveInfo: archiveSearchResponsesInterface;
  selectedOption?: {
    carOptionId: number;
    optionName: string;
  }[];
}

export const ArchiveCard = ({
  archiveInfo,
  selectedOption,
}: ArchiveCardProps) => {
  return (
    <>
      {archiveInfo.carArchiveResult && (
        <Wrapper direction="column" justify="flex-start" align="flex-start">
          <Flex height="auto" justify="space-between" margin="0 0 5px 0">
            <Text typo="Heading3_Bold">
              {`팰리세이드 ${
                archiveInfo.carArchiveResult.모델 &&
                archiveInfo.carArchiveResult.모델[0]
              }`}
            </Text>
            <BrownTag>{`${getDate(new Date(archiveInfo.dayTime))}에 ${
              archiveInfo.type
            }했어요`}</BrownTag>
          </Flex>
          <Text typo="Body3_Regular">{`${archiveInfo.carArchiveResult.트림.join(
            '/',
          )}`}</Text>
          <Flex
            height="auto"
            gap={12}
            justify="flex-start"
            margin="12px 0 10px 0"
          >
            <Text typo="Body3_Medium">외장</Text>
            <Text typo="Body3_Regular" palette="DarkGray">
              {`${
                archiveInfo.carArchiveResult.외장색상 &&
                archiveInfo.carArchiveResult.외장색상[0]
              }`}
            </Text>
            <Text typo="Body3_Medium">내장</Text>
            <Text typo="Body3_Regular" palette="DarkGray">
              {`${
                archiveInfo.carArchiveResult.내장색상 &&
                archiveInfo.carArchiveResult.내장색상[0]
              }`}
            </Text>
          </Flex>
          <Flex height="auto" gap={7} justify="flex-start" align="flex-start">
            <Text
              typo="Body3_Medium"
              css={css`
                white-space: nowrap;
              `}
            >
              선택옵션
            </Text>
            <Flex
              height="auto"
              justify="flex-start"
              gap={8}
              css={css`
                flex-wrap: wrap;
              `}
            >
              {archiveInfo.carArchiveResult['선택품목'].map(
                (optionName, idx) => {
                  const isSelected =
                    selectedOption?.some(
                      (option) => option.optionName === optionName,
                    ) || false;
                  return (
                    <OptionChip key={`상세품목_${idx}`} isSelected={isSelected}>
                      <Text key={`상세품목_${idx}`} typo="Body3_Regular">
                        {optionName}
                      </Text>
                    </OptionChip>
                  );
                },
              )}
            </Flex>
          </Flex>
          <Flex
            height="auto"
            justify="flex-start"
            gap={8}
            margin="17px 0 0 0"
            css={css`
              flex-wrap: wrap;
            `}
          >
            {archiveInfo.tags &&
              archiveInfo.tags.map(({ tagContent }, idx) => (
                <Tag desc={tagContent} key={`tagContent_${idx}`} />
              ))}
          </Flex>
        </Wrapper>
      )}
    </>
  );
};

const Wrapper = styled(Flex)`
  width: 508px;
  height: auto;
  border: 3px solid ${theme.palette.Sand};
  border-radius: 8px;
  padding: 30px 20px;
  cursor: pointer;

  &:hover {
    background-color: ${theme.palette.LightSand};
  }
  transition: 0.3s ease;
`;

const BrownTag = styled(Flex)`
  display: flex;
  width: auto;
  height: 24px;
  padding: 2px 12px;
  justify-content: center;
  align-items: center;
  gap: 10px;
  flex-shrink: 0;

  border-radius: 16px;
  background-color: ${theme.palette.LightSand};
  ${theme.typo.Body4_Medium}
  color: ${theme.palette.Gold};
`;
