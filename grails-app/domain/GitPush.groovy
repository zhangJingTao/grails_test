/**
 * 存储push信息
 */
class GitPush {

    static constraints = {
    }
    /**
     * 项目信息
     */
    String repositoryName
    String repositoryUrl
    String repositoryDesc
    String repositoryHomePage
    /**
     * Commits信息
     */
    String commitId
    String commitDate
    String commitUrl
    String commitAuthorName
    String commitAuthorEmail
    String commitMsg;
}
