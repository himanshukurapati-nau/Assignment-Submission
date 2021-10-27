package com.himanshusampath.assignmentcreationfaculty;

public class AssignmentModel
{
    String studentname,rollnumber,submitteddate,uri,assignmentid;

    AssignmentModel()
    {

    }

    public AssignmentModel(String studentname, String rollnumber, String submitteddate, String uri, String assignmentid)
    {
        this.studentname = studentname;
        this.rollnumber = rollnumber;
        this.submitteddate = submitteddate;
        this.uri = uri;
        this.assignmentid = assignmentid;
    }

    public String getStudentname()
    {
        return studentname;
    }

    public void setStudentname(String studentname)
    {
        this.studentname = studentname;
    }

    public String getRollnumber()
    {
        return rollnumber;
    }

    public void setRollnumber(String rollnumber)
    {
        this.rollnumber = rollnumber;
    }

    public String getSubmitteddate()
    {
        return submitteddate;
    }

    public void setSubmitteddate(String submitteddate)
    {
        this.submitteddate = submitteddate;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getAssignmentid()
    {
        return assignmentid;
    }

    public void setAssignmentid(String assignmentid)
    {
        this.assignmentid = assignmentid;
    }
}
