package com.himanshusampath.assignmentsubmission;

public class model
{
    String subjectname,assignmentname, enddate;

    model()
    {

    }

    public model(String subjectname, String assignmentname, String enddate)
    {
        this.subjectname = subjectname;
        this.assignmentname = assignmentname;
        this.enddate = enddate;
    }

    public String getSubjectname()
    {
        return subjectname;
    }

    public void setSubjectname(String subjectname)
    {
        this.subjectname = subjectname;
    }

    public String getAssignmentname()
    {
        return assignmentname;
    }

    public void setAssignmentname(String assignmentname)
    {
        this.assignmentname = assignmentname;
    }

    public String getEnddate()
    {
        return enddate;
    }

    public void setEnddate(String enddate)
    {
        this.enddate = enddate;
    }
}
