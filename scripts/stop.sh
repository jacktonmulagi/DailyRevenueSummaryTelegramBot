#!/bin/sh

if [ `ps -ef | grep java | grep -v grep | grep "DailyRevenueSummaryTelegramBot" |wc -l` -gt 0 ]
	then
		echo "Stopping DailyRevenueSummaryTelegramBot..."
		sleep 2;
		ps -ef | grep java | grep -v grep | grep "DailyRevenueSummaryTelegramBot" | awk {'print$2'}| xargs kill
		echo "Stopped DailyRevenueSummaryTelegramBot"
	else
		echo 'DailyRevenueSummaryTelegramBot is not running '
fi

exit 0;