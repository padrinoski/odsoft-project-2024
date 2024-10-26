# Pipeline Performance Analysis

rascunho: 

criar 2 jenkinsfile (1 sem improvements  + 1 COM improvements)

falar sobre sonarcloud vs sonarcube server local
falar sobre caching & artifact stashing - https://www.aviator.co/blog/how-to-optimize-jenkins-pipeline-performance/#
preserveStashes()

falar sobre paralelismo - https://www.aviator.co/blog/how-to-optimize-jenkins-pipeline-performance/#
https://www.baeldung.com/ops/running-stages-in-parallel-jenkins-workflow-pipeline
falar sobre -Dskiptests na stage de build


https://stackoverflow.com/questions/54866575/jenkins-stash-vs-archiveartifacts - DenCowboy
stash is used to "save" some files in a pipeline stage and reuse them on a different slave (unstash). Stash is only useful when you have a small set of files. It will become very slow when you want to stash a big amount of data. If you need to stash a lot of files it's recommended to use a shared filesystem between your slaves so the content of your workspace can be used by multiple slaves.

Archiving artifacts will save artifacts on the master slave. You can specify if you only want to archive the generated artifacts from the last build or more. This is useful when you have some deploy job on your master to deploy the artifacts after a succesful run or to make them available in your jenkins console.

## 1. Baseline Performance
### 1.1. Initial Metrics
- **Build Time**: [Initial build time]
- **Test Execution Time**: [Initial test execution time]
- **Deployment Time**: [Initial deployment time]

### 1.2. Bottlenecks
- **Bottleneck 1**: [Description of bottleneck]
- **Bottleneck 2**: [Description of bottleneck]

## 2. Improvements
### 2.1. Changes Implemented
- **Change 1**: [Description of change]
- **Change 2**: [Description of change]

### 2.2. Metrics After Improvements
- **Build Time**: [Improved build time]
- **Test Execution Time**: [Improved test execution time]
- **Deployment Time**: [Improved deployment time]

## 3. Evidence of Improvement
### 3.1. Comparison
- **Build Time**: [Initial vs. Improved]
- **Test Execution Time**: [Initial vs. Improved]
- **Deployment Time**: [Initial vs. Improved]

### 3.2. Graphs and Charts
- **Build Time Over Time**: [Graph showing build time improvement]
- **Test Execution Time Over Time**: [Graph showing test execution time improvement]
- **Deployment Time Over Time**: [Graph showing deployment time improvement]