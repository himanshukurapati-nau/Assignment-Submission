package com.himanshusampath.assignmentcreationfaculty;

public class model
{
    String subjectname,assignmentname, enddate,branch,uri,uuid;

    model()
    {

    }



    public model(String subjectname, String assignmentname, String enddate, String branch, String uri)
    {
        this.subjectname = subjectname;
        this.assignmentname = assignmentname;
        this.enddate = enddate;
        this.branch = branch;
        this.uri = uri;
        this.uuid=uuid;
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

    public String getBranch() { return branch; }

    public void setBranch(String branch)
    {
        this.branch = branch;
    }

    public String geturi()
    {
        return uri;
    }

    public void seturi(String uri)
    {
        this.uri = uri;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
