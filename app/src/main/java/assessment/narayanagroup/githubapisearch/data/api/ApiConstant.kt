package assessment.narayanagroup.githubapisearch.data.api

object ApiConstant {

    const val GET_REPO = "search/repositories?sort=stars"
    const val GET_CONT = "/repos/{owner}/{repo}/contributors"
    const val GET_PROJECT = "/repos/{owner}/{repo}/projects?query=open"
    const val GET_README = "/repos/{owner}/{repo}/readme"
}