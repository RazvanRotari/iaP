#!/bin/bash

CODE_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "Generating the model..."
python3 ${CODE_DIR}/../utils/create_god.py ${CODE_DIR}/../../docs/providers.yaml > ${CODE_DIR}/model.py
echo "Model done! Starting the inserter"
python3 ${CODE_DIR}/inserter.py
