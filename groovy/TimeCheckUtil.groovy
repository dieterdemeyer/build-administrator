class TimeCheckUtil {

    def quarterToSevenAMOnWeekdays = Calendar.getInstance()
    def tenMinutesToMidnightInWeekend = Calendar.getInstance()
    
    TimeCheckUtil() {
        initialiseTimes()
    }
    
    def initialiseTimes = {
        quarterToSevenAMOnWeekdays.set(Calendar.HOUR_OF_DAY, 6)
        quarterToSevenAMOnWeekdays.set(Calendar.MINUTE, 45)
        quarterToSevenAMOnWeekdays.set(Calendar.SECOND, 0)
      
        tenMinutesToMidnightInWeekend.set(Calendar.HOUR_OF_DAY, 23)
        tenMinutesToMidnightInWeekend.set(Calendar.MINUTE, 50)
        tenMinutesToMidnightInWeekend.set(Calendar.SECOND, 0)
    }
    
    def conditionsMetForRunningOnWeekdays = {
        def sysCalendar = Calendar.getInstance()
        
        return sysCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
               sysCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY &&
               sysCalendar.before(quarterToSevenAMOnWeekdays)
    }
      
    def conditionsMetForRunningInWeekend = {
        def sysCalendar = Calendar.getInstance()
        
        return (sysCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                sysCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) &&
                sysCalendar.before(tenMinutesToMidnightInWeekend)
    }
    
}